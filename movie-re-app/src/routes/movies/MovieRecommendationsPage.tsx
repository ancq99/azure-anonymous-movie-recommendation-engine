import {Component, createSignal, For, Show} from "solid-js";
import {MovieDTO} from "../../generated";
import {MoviesApiClient} from "../../config/api";
import {MovieCard} from "../../shared/components/movie/MovieCard";

type MovieRecommendationsPageProps = {
    viewedMovies: MovieDTO[]
}

export const MovieRecommendationsPage: Component<MovieRecommendationsPageProps> = props => {
    const [recommendedMovies, setRecommendedMovies] = createSignal<MovieDTO[]>([])

    MoviesApiClient.recommendMovies({movieDTO: props.viewedMovies})
        .then(movies => setRecommendedMovies(movies))

    return (
        <Show when={recommendedMovies().length > 0} fallback={
            <p>Loading recommendations...</p>}
        >

            <p>There are some recommendations for you:</p>
            <div class='flex flex-wrap justify-center'>
                <For each={recommendedMovies()}>
                    {movie => <MovieCard movie={movie} mode='view'/>}
                </For>
            </div>

        </Show>
    )
}