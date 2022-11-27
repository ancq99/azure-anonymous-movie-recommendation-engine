import {Configuration, MoviesApi} from "../generated";

const apiConfiguration = new Configuration({basePath: ""})

export const MoviesApiClient = new MoviesApi(apiConfiguration);