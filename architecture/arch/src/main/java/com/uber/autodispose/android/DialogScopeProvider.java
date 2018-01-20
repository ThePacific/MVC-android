/*
 * Copyright (C) 2017. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uber.autodispose.android;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.pacific.arch.gauva.Preconditions2;
import com.uber.autodispose.LifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.uber.autodispose.android.ViewLifecycleEvent.DETACH;

/**
 * A {@link LifecycleScopeProvider} that can provide scoping for Android {@link Dialog} classes.
 * <p>
 * <pre><code>
 *   AutoDispose.with(DialogScopeProvider.from(dialog));
 * </code></pre>
 */
public class DialogScopeProvider implements LifecycleScopeProvider<ViewLifecycleEvent> {

    private final LifecycleScopeProvider lifecycleScopeProvider;
    private final Dialog dialog;

    private DialogScopeProvider(final Dialog dialog) {
        this.dialog = dialog;
        this.lifecycleScopeProvider = ViewScopeProvider.from(dialog.getWindow().getDecorView());
    }

    /**
     * Creates a {@link LifecycleScopeProvider} for Android Dialog.
     *
     * @param dialog the dialog to scope for
     * @return a {@link LifecycleScopeProvider} against this dialog.
     */
    public static DialogScopeProvider from(@NonNull Dialog dialog) {
        Preconditions2.checkNotNull(dialog);
        return new DialogScopeProvider(dialog);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<ViewLifecycleEvent> lifecycle() {
        return lifecycleScopeProvider.lifecycle();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<ViewLifecycleEvent, ViewLifecycleEvent> correspondingEvents() {
        return lifecycleScopeProvider.correspondingEvents();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ViewLifecycleEvent peekLifecycle() {
        if (dialog.isShowing() || lifecycleScopeProvider.peekLifecycle() == ViewLifecycleEvent.ATTACH) {
            return ViewLifecycleEvent.ATTACH;
        }
        return DETACH;
    }
}
