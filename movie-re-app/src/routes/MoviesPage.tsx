import {Component} from "solid-js";
import {MovieDTO} from "../generated";
import {createStore} from "solid-js/store";
import {Route, Routes, useNavigate} from "@solidjs/router";
import {MoviePickerPage} from "./movies/MoviePickerPage";
import {MovieRecommendationsPage} from "./movies/MovieRecommendationsPage";

export const MoviesPage: Component = () => {
    const [viewedMovies, setViewedMovies] = createStore<MovieDTO[]>([]);
    const navigate = useNavigate();

    const addMovie = (movie: MovieDTO) => {
        setViewedMovies([...viewedMovies, movie]);
    }
    const recommendation = () => navigate("/movie-recommendations");

    return <>
        <Routes>
            <Route path="/" element={<a href="/pick-movies">Pick</a>}/>
            <Route path="/pick-movies" element={
                <MoviePickerPage viewedMovies={viewedMovies}
                                 addMovie={addMovie}
                                 recommendMovies={recommendation}/>}
            />
            <Route path={"/movie-recommendations"} element={
                <MovieRecommendationsPage viewedMovies={viewedMovies}/>}
            />
        </Routes>
    </>
}