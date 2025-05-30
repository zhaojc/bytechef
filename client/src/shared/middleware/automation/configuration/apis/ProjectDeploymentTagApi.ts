/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Configuration Internal API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  Tag,
  UpdateTagsRequest,
} from '../models/index';
import {
    TagFromJSON,
    TagToJSON,
    UpdateTagsRequestFromJSON,
    UpdateTagsRequestToJSON,
} from '../models/index';

export interface UpdateProjectDeploymentTagsRequest {
    id: number;
    updateTagsRequest: UpdateTagsRequest;
}

/**
 * 
 */
export class ProjectDeploymentTagApi extends runtime.BaseAPI {

    /**
     * Get project deployment tags.
     * Get project deployment tags
     */
    async getProjectDeploymentTagsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<Tag>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/project-deployments/tags`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(TagFromJSON));
    }

    /**
     * Get project deployment tags.
     * Get project deployment tags
     */
    async getProjectDeploymentTags(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<Tag>> {
        const response = await this.getProjectDeploymentTagsRaw(initOverrides);
        return await response.value();
    }

    /**
     * Updates tags of an existing project deployment.
     * Updates tags of an existing project deployment
     */
    async updateProjectDeploymentTagsRaw(requestParameters: UpdateProjectDeploymentTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling updateProjectDeploymentTags().'
            );
        }

        if (requestParameters['updateTagsRequest'] == null) {
            throw new runtime.RequiredError(
                'updateTagsRequest',
                'Required parameter "updateTagsRequest" was null or undefined when calling updateProjectDeploymentTags().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/project-deployments/{id}/tags`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: UpdateTagsRequestToJSON(requestParameters['updateTagsRequest']),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Updates tags of an existing project deployment.
     * Updates tags of an existing project deployment
     */
    async updateProjectDeploymentTags(requestParameters: UpdateProjectDeploymentTagsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.updateProjectDeploymentTagsRaw(requestParameters, initOverrides);
    }

}
