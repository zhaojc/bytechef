/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Execution Internal API
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
 * 
 * @export
 * @interface WebhookRetry
 */
export interface WebhookRetry {
    /**
     * 
     * @type {number}
     * @memberof WebhookRetry
     */
    initialInterval?: number;
    /**
     * 
     * @type {number}
     * @memberof WebhookRetry
     */
    maxInterval?: number;
    /**
     * 
     * @type {number}
     * @memberof WebhookRetry
     */
    maxAttempts?: number;
    /**
     * 
     * @type {number}
     * @memberof WebhookRetry
     */
    multiplier?: number;
}

/**
 * Check if a given object implements the WebhookRetry interface.
 */
export function instanceOfWebhookRetry(value: object): value is WebhookRetry {
    return true;
}

export function WebhookRetryFromJSON(json: any): WebhookRetry {
    return WebhookRetryFromJSONTyped(json, false);
}

export function WebhookRetryFromJSONTyped(json: any, ignoreDiscriminator: boolean): WebhookRetry {
    if (json == null) {
        return json;
    }
    return {
        
        'initialInterval': json['initialInterval'] == null ? undefined : json['initialInterval'],
        'maxInterval': json['maxInterval'] == null ? undefined : json['maxInterval'],
        'maxAttempts': json['maxAttempts'] == null ? undefined : json['maxAttempts'],
        'multiplier': json['multiplier'] == null ? undefined : json['multiplier'],
    };
}

export function WebhookRetryToJSON(json: any): WebhookRetry {
    return WebhookRetryToJSONTyped(json, false);
}

export function WebhookRetryToJSONTyped(value?: WebhookRetry | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'initialInterval': value['initialInterval'],
        'maxInterval': value['maxInterval'],
        'maxAttempts': value['maxAttempts'],
        'multiplier': value['multiplier'],
    };
}

