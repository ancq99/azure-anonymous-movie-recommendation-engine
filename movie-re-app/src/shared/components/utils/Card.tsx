import {ParentComponent} from "solid-js";
import "../../../index.css"

export type CardProps = {
    size?: 'sm' | 'md' | 'lg';
    onClick?: (event: MouseEvent) => any;
    class?: string
}

export const Card: ParentComponent<CardProps> = (props) => {
    const size = !props.size || props.size === 'md' ? 6 : props.size === 'sm' ? 3.5 : 10;

    return (
        <div class={'center rounded-2xl relative p-2 border-2 border-white bg-white-alpha-500 '
            + props.class}
             style={`width: ${size}em; height: ${size}em`}
             onClick={props.onClick}
        >
            {props.children}
        </div>
    )
}