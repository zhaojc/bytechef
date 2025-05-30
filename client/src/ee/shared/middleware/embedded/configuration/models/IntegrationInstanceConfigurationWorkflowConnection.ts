/* tslint:disable */
/* eslint-disable */
/**
 * The Embedded Configuration Internal API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * The connection used in a particular task or trigger.
 * @export
 * @interface IntegrationInstanceConfigurationWorkflowConnection
 */
export interface IntegrationInstanceConfigurationWorkflowConnection {
    /**
     * The connection id
     * @type {number}
     * @memberof IntegrationInstanceConfigurationWorkflowConnection
     */
    connectionId?: number;
    /**
     * The connection key under which a connection is defined in a workflow definition.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationWorkflowConnection
     */
    workflowConnectionKey?: string;
    /**
     * The action/trigger name to which a connection belongs.
     * @type {string}
     * @memberof IntegrationInstanceConfigurationWorkflowConnection
     */
    workflowNodeName?: string;
}

/**
 * Check if a given object implements the IntegrationInstanceConfigurationWorkflowConnection interface.
 */
export function instanceOfIntegrationInstanceConfigurationWorkflowConnection(value: object): value is IntegrationInstanceConfigurationWorkflowConnection {
    return true;
}

export function IntegrationInstanceConfigurationWorkflowConnectionFromJSON(json: any): IntegrationInstanceConfigurationWorkflowConnection {
    return IntegrationInstanceConfigurationWorkflowConnectionFromJSONTyped(json, false);
}

export function IntegrationInstanceConfigurationWorkflowConnectionFromJSONTyped(json: any, ignoreDiscriminator: boolean): IntegrationInstanceConfigurationWorkflowConnection {
    if (json == null) {
        return json;
    }
    return {
        
        'connectionId': json['connectionId'] == null ? undefined : json['connectionId'],
        'workflowConnectionKey': json['workflowConnectionKey'] == null ? undefined : json['workflowConnectionKey'],
        'workflowNodeName': json['workflowNodeName'] == null ? undefined : json['workflowNodeName'],
    };
}

export function IntegrationInstanceConfigurationWorkflowConnectionToJSON(json: any): IntegrationInstanceConfigurationWorkflowConnection {
    return IntegrationInstanceConfigurationWorkflowConnectionToJSONTyped(json, false);
}

export function IntegrationInstanceConfigurationWorkflowConnectionToJSONTyped(value?: IntegrationInstanceConfigurationWorkflowConnection | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'connectionId': value['connectionId'],
        'workflowConnectionKey': value['workflowConnectionKey'],
        'workflowNodeName': value['workflowNodeName'],
    };
}

