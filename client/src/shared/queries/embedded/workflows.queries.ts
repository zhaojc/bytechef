import {Workflow, WorkflowApi} from '@/ee/shared/middleware/embedded/configuration';

/* eslint-disable sort-keys */
import {useQuery} from '@tanstack/react-query';

export const WorkflowKeys = {
    workflow: (id: string) => [...WorkflowKeys.workflows, id],
    workflows: ['embeddedWorkflows'],
};

export const useGetWorkflowQuery = (id: string, enabled?: boolean) =>
    useQuery<Workflow, Error>({
        queryKey: WorkflowKeys.workflow(id),
        queryFn: () => new WorkflowApi().getWorkflow({id}),
        enabled: enabled === undefined ? true : enabled,
    });
