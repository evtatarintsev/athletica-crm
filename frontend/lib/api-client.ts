import {Configuration, DefaultApi} from '@athletica/client';


const apiConfig = new Configuration({
    basePath: "http://localhost:3000",
});

export const apiClient = new DefaultApi(apiConfig);
