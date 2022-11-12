import {Configuration, MoviesApi} from "../generated";

const apiConfiguration = new Configuration({basePath: "http://localhost:8080"})

export const MoviesApiClient = new MoviesApi(apiConfiguration);