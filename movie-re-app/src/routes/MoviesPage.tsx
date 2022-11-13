import {Component, For, Show} from "solid-js";
import {MovieDTO} from "../generated";
import {createStore} from "solid-js/store";
import {MovieSearchList} from "../shared/components/movie/MovieSearchList";
import {MovieCard} from "../shared/components/movie/MovieCard";

export const MoviesPage: Component = () => {
    const [viewedMovies, setViewedMovies] = createStore<MovieDTO[]>([]);

    const addMovie = (movie: MovieDTO) => {
        setViewedMovies([...viewedMovies, movie]);
    }

    return (
        <div class="w-screen mx-auto h-screen overflow-y-auto flex flex-col justify-start">

            <div class='h-5/6 w-3/4 mx-auto'>
                <MovieSearchList addMovie={addMovie}/>
            </div>

            <div class='h-1/6 px-2 v-stack bg-gray-800 border-t-2 border-t-white'>
                <p class="text-2xl font-bold">Movies you like:</p>

                <div class='w-full h-stack'>
                    <Show when={viewedMovies.length > 0}
                          fallback={<p class='text-2xl flex-grow-[5]'>It is empty here please add something from movie
                              list</p>}
                    >

                        <div class="w-5/6 flex justify-start flex-row overflow-x-auto">
                            <For each={viewedMovies}>
                                {item => <MovieCard movie={item} mode={'simplified'}/>}
                            </For>
                        </div>
                    </Show>

                    <div class='flex-grow-[2]'/>
                    <button class='btn-success btn-xl text-2xl font-extrabold m-2'>
                        Go
                    </button>
                </div>


            </div>

        </div>
    )
}