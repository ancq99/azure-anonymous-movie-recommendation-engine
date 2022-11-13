import {Component, Show} from "solid-js";
import {MovieDTO} from "../../../generated";
import {Card} from "../utils/Card";

export type MovieCardProps = {
    movie: MovieDTO

    mode?: 'view' | 'add' | 'simplified'
    onAdd?: (movie: MovieDTO) => any
}

export const MovieCard: Component<MovieCardProps> = props => {
    return (
        <Card class="m-2 w-60 min-w-[200px] v-stack relative">
            <div class='center text-center text-lg min-h-[3em]'>{props.movie.title}</div>


            <Show when={props.mode !== 'simplified'}>
                <div class='border-t-2 border-white w-full'/>
                <table class='w-full'>
                    <tbody>
                    <tr>
                        <td>Year:</td>
                        <td class='text-right'>2000</td>
                    </tr>
                    </tbody>
                </table>
            </Show>

            <Show when={props.mode === 'add'}>
                <div class='w-full h-8'></div>
                <button class='btn-md btn-success absolute bottom-2 w-32'
                        onClick={() => props.onAdd && props.onAdd(props.movie)}>
                    Add
                </button>
            </Show>
        </Card>
    )
}