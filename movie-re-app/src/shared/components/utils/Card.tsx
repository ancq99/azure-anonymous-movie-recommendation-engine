import {ParentComponent} from "solid-js";
import "../../../index.css"

export type CardProps = {
    onClick?: (event: MouseEvent) => any;
    class?: string
}

export const Card: ParentComponent<CardProps> = (props) => {
    return (
        <div class={'center rounded-2xl relative p-2  bg-white-alpha-500 ' +
            'overflow-hidden ' + props.class}
             onClick={props.onClick}
        >
            {props.children}
        </div>
    )
}