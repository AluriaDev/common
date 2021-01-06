package io.github.aluria.common.event.wrappers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public class CancellableEventWrapper extends EventWrapper implements Cancellable {

    @Setter
    @Getter
    private boolean cancelled;

}