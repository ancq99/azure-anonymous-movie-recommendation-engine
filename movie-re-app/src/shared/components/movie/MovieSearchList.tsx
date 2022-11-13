import {Component, createSignal, For, Match, Switch} from "solid-js";
import {MovieDTO} from "../../../generated";
import {MoviesApiClient} from "../../../config/api";
import {MovieCard} from "./MovieCard";

export type MovieSearchListProps = {
    addMovie: (movie: MovieDTO) => any
}

export const MovieSearchList: Component<MovieSearchListProps> = (props) => {
    const [movieTitle, setMovieTitle] = createSignal("")
    const [movies, setMovies] = createSignal<MovieDTO[]>([])
    const [loading, setLoading] = createSignal(false)

    const fetchMovies = () => {
        setLoading(true);
        MoviesApiClient
            .getMovies({name: movieTitle()})
            .then(movies => setMovies(movies));
        setLoading(false);
    }

    const onTitleChange = (event: any) => {
        const newTitle = event.currentTarget.value;
        setMovieTitle(newTitle);
        if (newTitle.length >= 3) {
            fetchMovies();
        } else {
            setMovies([])
        }
    }

    return (
        <div class='px-4 v-stack w-full h-full relative'>
            <div class='relative h-1/6 flex flex-col justify-evenly'>
                <p class='text-2xl font-bold'>Please search and movies you like:</p>
                <input class='text-input-md w-full' onInput={onTitleChange}
                       placeholder="Please enter at least 3 characters..."/>
            </div>

            <div class='flex justify-center items-start flex-wrap w-full h-5/6 overflow-y-auto'>
                <Switch>
                    <Match when={movieTitle().length < 3}>Please enter at least 3 character</Match>
                    <Match when={loading()}>Please wait your results are loading...</Match>
                    <Match when={!loading() && movies().length > 0}>
                        <For each={movies()}>
                            {(item) => <MovieCard movie={item} onAdd={props.addMovie} mode='add'/>}
                        </For>
                    </Match>
                    <Match when={!loading() && movies()}>
                        No results found.
                    </Match>
                </Switch>
            </div>

        </div>
    )
}
