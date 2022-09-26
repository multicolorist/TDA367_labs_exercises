package com.chalmers.group30.models.utilities;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

public interface CacheUpdateProvider<T> {
    @NotNull
    T getNewDataToCache() throws IOException;
}
