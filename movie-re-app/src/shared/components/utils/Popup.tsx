import {ParentComponent, Show} from "solid-js";
import "../../styles/components.css"
import "../../../index.css"

export type PopupProps = {
    onClose: () => any;
    isOpen: boolean;
}

export const Popup: ParentComponent<PopupProps> = (props) => {
    return (
        <Show when={props.isOpen}>
            <div class="fixed zIndex-10 w-screen h-screen top-0 left-0
                        bg-[#888888AA] center">

                <div class="fixed zIndex-20 rounded-2xl bg-slate-800 min-w-[40vw] min-h-[40vh]">
                    {props.children}

                    <button class="btn-icon text-2xl absolute top-2 right-2"
                            onClick={props.onClose}
                    >
                        X
                    </button>
                </div>

            </div>
        </Show>
    )
}