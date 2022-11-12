import {Component, createSignal, For} from "solid-js";
import {MovieDTO} from "../../../generated";
import {MoviesApiClient} from "../../../config/api";

export type MovieSearchListProps = {
    addMovie: (movie: MovieDTO) => any
}

export const MovieSearchList: Component<MovieSearchListProps> = (props) => {
    const [movieTitle, setMovieTitle] = createSignal("")
    const [movies, setMovies] = createSignal<MovieDTO[]>([])

    const fetchMovies = () => MoviesApiClient
        .getMovies({name: movieTitle()})
        .then(movies => setMovies(movies));

    const onTitleChange = (event: any) => {
        setMovieTitle(event.currentTarget.value);
        fetchMovies();
    }

    return (
        <div>
            <div class="h-stack">
                <input class='text-input-md' onChange={onTitleChange}/>
            </div>

            <div style="v-stack">
                <For each={movies()}>
                    {(item) => <MovieInList movie={item} onAdd={props.addMovie}/>}
                </For>
            </div>
        </div>
    )
}

type MovieInListProps = {
    movie: MovieDTO;
    onAdd: (movie: MovieDTO) => any;
}

const MovieInList: Component<MovieInListProps> = (props) => {
    return (
        <div class="h-stack w-full text-lg my-1.5 card-sm text-2xl">
            <span>{props.movie.title}</span>
            <p class="flex-grow"></p>
            <button class="btn-icon text-2xl center"
                    onClick={() => props.onAdd(props.movie)}>+</button>
        </div>
    )
}