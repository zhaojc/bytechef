/* eslint-disable sort-keys */
import {IntegrationTagApi, Tag} from '@/ee/shared/middleware/embedded/configuration';
import {useQuery} from '@tanstack/react-query';

export const IntegrationTagKeys = {
    integrationTags: ['integrationTags'] as const,
};

export const useGetIntegrationTagsQuery = () =>
    useQuery<Tag[], Error>({
        queryKey: IntegrationTagKeys.integrationTags,
        queryFn: () => new IntegrationTagApi().getIntegrationTags(),
    });
