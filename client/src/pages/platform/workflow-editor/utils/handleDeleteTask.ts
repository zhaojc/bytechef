import useWorkflowTestChatStore from '@/pages/platform/workflow-editor/stores/useWorkflowTestChatStore';
import {SPACE} from '@/shared/constants';
import {Workflow, WorkflowTask} from '@/shared/middleware/platform/configuration';
import {ProjectWorkflowKeys} from '@/shared/queries/automation/projectWorkflows.queries';
import {IntegrationWorkflowKeys} from '@/shared/queries/embedded/integrationWorkflows.queries';
import {WorkflowNodeOutputKeys} from '@/shared/queries/platform/workflowNodeOutputs.queries';
import {BranchCaseType, NodeDataType, WorkflowDefinitionType, WorkflowTaskType} from '@/shared/types';
import {QueryClient, UseMutationResult} from '@tanstack/react-query';

import {WorkflowTaskDataType} from '../stores/useWorkflowDataStore';
import useWorkflowNodeDetailsPanelStore from '../stores/useWorkflowNodeDetailsPanelStore';
import {TASK_DISPATCHER_CONFIG} from './taskDispatcherConfig';

interface HandleDeleteTaskProps {
    rootClusterElementNodeData?: NodeDataType;
    clusterElementsCanvasOpen?: boolean;
    currentNode?: NodeDataType;
    data: NodeDataType;
    integrationId?: number;
    projectId?: number;
    queryClient: QueryClient;
    setRootClusterElementNodeData?: (node: NodeDataType) => void;
    setCurrentNode?: (node: NodeDataType) => void;
    updateWorkflowMutation: UseMutationResult<void, unknown, {id: string; workflow: Workflow}>;
    workflow: Workflow & WorkflowTaskDataType;
}

export default function handleDeleteTask({
    clusterElementsCanvasOpen,
    currentNode,
    data,
    integrationId,
    projectId,
    queryClient,
    rootClusterElementNodeData,
    setCurrentNode,
    setRootClusterElementNodeData,
    updateWorkflowMutation,
    workflow,
}: HandleDeleteTaskProps) {
    if (!workflow?.definition) {
        return;
    }

    const workflowDefinition: WorkflowDefinitionType = JSON.parse(workflow?.definition);

    const {tasks: workflowTasks} = workflowDefinition;

    if (!workflowTasks) {
        return;
    }

    let updatedTasks: Array<WorkflowTaskType>;

    if (data.conditionData) {
        const parentConditionTask = TASK_DISPATCHER_CONFIG.condition.getTask({
            taskDispatcherId: data.conditionData.conditionId,
            tasks: workflowTasks,
        });

        const taskConditionCase = data.conditionData.conditionCase;

        if (!parentConditionTask?.parameters) {
            return;
        }

        parentConditionTask.parameters[taskConditionCase as string] = (
            parentConditionTask.parameters[taskConditionCase] as Array<WorkflowTask>
        ).filter((childTask) => childTask.name !== data.name);

        updatedTasks = workflowTasks.map((task) => {
            if (task.name !== parentConditionTask.name) {
                return task;
            }

            return parentConditionTask;
        }) as Array<WorkflowTaskType>;
    } else if (data.loopData) {
        const parentLoopTask = TASK_DISPATCHER_CONFIG.loop.getTask({
            taskDispatcherId: data.loopData.loopId,
            tasks: workflowTasks,
        });

        if (!parentLoopTask?.parameters) {
            return;
        }

        parentLoopTask.parameters.iteratee = (parentLoopTask.parameters.iteratee as Array<WorkflowTask>).filter(
            (childTask) => childTask.name !== data.name
        );

        updatedTasks = workflowTasks.map((task) => {
            if (task.name !== parentLoopTask.name) {
                return task;
            }

            return parentLoopTask;
        }) as Array<WorkflowTaskType>;
    } else if (data.branchData) {
        const parentBranchTask = TASK_DISPATCHER_CONFIG.branch.getTask({
            taskDispatcherId: data.branchData.branchId,
            tasks: workflowTasks,
        });

        if (!parentBranchTask?.parameters) {
            return;
        }

        const {caseKey} = data.branchData;
        const {name, parameters} = parentBranchTask;

        if (caseKey === 'default') {
            parentBranchTask.parameters.default = (parameters.default as Array<WorkflowTask>).filter(
                (childTask) => childTask.name !== data.name
            );
        } else if (parameters.cases) {
            parameters.cases = (parameters.cases as BranchCaseType[]).map((caseItem) => {
                if (caseItem.key === caseKey) {
                    return {
                        ...caseItem,
                        tasks: (caseItem.tasks || []).filter((childTask) => childTask.name !== data.name),
                    };
                }

                return caseItem;
            });
        }

        updatedTasks = workflowTasks.map((task) => {
            if (task.name !== name) {
                return task;
            }

            return parentBranchTask;
        }) as Array<WorkflowTaskType>;
    } else if (data.parallelData) {
        const parentParallelTask = TASK_DISPATCHER_CONFIG.parallel.getTask({
            taskDispatcherId: data.parallelData.parallelId,
            tasks: workflowTasks,
        });

        if (!parentParallelTask?.parameters) {
            return;
        }

        parentParallelTask.parameters.tasks = (parentParallelTask.parameters.tasks as Array<WorkflowTask>).filter(
            (childTask) => childTask.name !== data.name
        );

        updatedTasks = workflowTasks.map((task) => {
            if (task.name !== parentParallelTask.name) {
                return task;
            }

            return parentParallelTask;
        }) as Array<WorkflowTaskType>;
    } else if (clusterElementsCanvasOpen && rootClusterElementNodeData) {
        const currentClusterElementType = data.clusterElementType;
        const clusterElementName = data.name;
        const rootClusterElementTask = workflowTasks.find((task) => task.name === rootClusterElementNodeData?.name);
        const updatedClusterElements = {...rootClusterElementNodeData.clusterElements};

        if (rootClusterElementTask && rootClusterElementTask.clusterElements) {
            const clusterElementValue =
                rootClusterElementTask.clusterElements[
                    currentClusterElementType as keyof typeof rootClusterElementTask.clusterElements
                ];

            if (Array.isArray(clusterElementValue) && currentClusterElementType !== undefined) {
                updatedClusterElements[currentClusterElementType] = clusterElementValue.filter(
                    (element) => element.name !== clusterElementName
                );

                rootClusterElementTask.clusterElements[
                    currentClusterElementType as keyof typeof rootClusterElementTask.clusterElements
                ] = [...updatedClusterElements[currentClusterElementType]];
            } else {
                updatedClusterElements[
                    currentClusterElementType as keyof typeof rootClusterElementTask.clusterElements
                ] = null;

                rootClusterElementTask.clusterElements[
                    currentClusterElementType as keyof typeof rootClusterElementTask.clusterElements
                ] = null;
            }
        }

        if (setRootClusterElementNodeData && setCurrentNode) {
            if (currentNode?.rootClusterElement) {
                setCurrentNode({
                    ...currentNode,
                    clusterElements: updatedClusterElements,
                });
            } else {
                setRootClusterElementNodeData({
                    ...rootClusterElementNodeData,
                    clusterElements: updatedClusterElements,
                });
            }

            if (currentNode?.name === data.name) {
                useWorkflowNodeDetailsPanelStore.getState().reset();

                setCurrentNode({
                    ...rootClusterElementNodeData,
                    clusterElements: updatedClusterElements,
                });

                useWorkflowNodeDetailsPanelStore.getState().setWorkflowNodeDetailsPanelOpen(true);
            }
        }

        updatedTasks = workflowTasks.map((task) => {
            if (task.name !== rootClusterElementTask?.name) {
                return task;
            }

            return rootClusterElementTask;
        }) as Array<WorkflowTaskType>;
    } else {
        updatedTasks = workflowTasks.filter((task: WorkflowTask) => task.name !== data.name);
    }

    updateWorkflowMutation.mutate(
        {
            id: workflow.id!,
            workflow: {
                definition: JSON.stringify(
                    {
                        ...workflowDefinition,
                        tasks: updatedTasks,
                    },
                    null,
                    SPACE
                ),
                version: workflow.version,
            },
        },
        {
            onSuccess: () => {
                queryClient.invalidateQueries({
                    queryKey: WorkflowNodeOutputKeys.filteredPreviousWorkflowNodeOutputs({
                        id: workflow.id!,
                        lastWorkflowNodeName: currentNode?.name,
                    }),
                });

                if (projectId) {
                    queryClient.invalidateQueries({
                        queryKey: ProjectWorkflowKeys.projectWorkflows(projectId),
                    });

                    queryClient.invalidateQueries({
                        queryKey: ProjectWorkflowKeys.workflows,
                    });
                }

                if (integrationId) {
                    queryClient.invalidateQueries({
                        queryKey: IntegrationWorkflowKeys.integrationWorkflows(integrationId),
                    });
                }

                if (currentNode?.name === data.name && !currentNode?.clusterElementType) {
                    useWorkflowNodeDetailsPanelStore.getState().reset();
                    useWorkflowTestChatStore.getState().setWorkflowTestChatPanelOpen(false);
                }
            },
        }
    );
}
