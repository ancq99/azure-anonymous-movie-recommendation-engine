import {Component, createSignal, For} from "solid-js";
import {MovieDTO} from "../generated";
import {createStore} from "solid-js/store";
import {MovieSearchList} from "../shared/components/movie/MovieSearchList";
import {Card} from "../shared/components/utils/Card";
import {Popup} from "../shared/components/utils/Popup";

export const MoviesPage: Component = () => {
    const [viewedMovies, setViewedMovies] = createStore<MovieDTO[]>([]);
    const [addMovieOpen, setAddMovieOpen] = createSignal(false)

    const addMovie = (movie: MovieDTO) => {
        setAddMovieOpen(false);
        setViewedMovies([...viewedMovies, movie]);
    }

    return (
        <div class="v-stack w-screen min-h-[20em] py-10">

            <p class="text-3xl font-bold">Please add movies you have watched:</p>
            <div class="flex justify-center">
                <For each={viewedMovies}>
                    {item =>
                        <Card class="m-2">
                            <span class='text-center text-xl'>{item.title}</span>
                        </Card>
                    }
                </For>
                <Card class="hover:cursor-pointer m-2" onClick={() => setAddMovieOpen(true)}>
                    <span class='text-center text-3xl font-extrabold'>+</span>
                </Card>
            </div>

            <Popup onClose={() => setAddMovieOpen(false)}
                   isOpen={addMovieOpen()}>
                <div class="center mt-4">
                    <MovieSearchList addMovie={addMovie}/>
                </div>
            </Popup>
        </div>
    )
}