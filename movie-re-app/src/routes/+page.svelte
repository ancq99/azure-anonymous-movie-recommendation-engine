<script lang="ts">
    import {Configuration, MoviesApi} from "../generated";

    let movies: any[] = [];
    let loaded = false;

    new MoviesApi(new Configuration({basePath: 'http://localhost:8080'})).getMovies()
        .then(m => {
            console.log(m)
            movies = m;
            loaded = true;
        })

</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>

{#if loaded}
    {#each movies as movie}
        {movie.title}
    {/each}
{/if}