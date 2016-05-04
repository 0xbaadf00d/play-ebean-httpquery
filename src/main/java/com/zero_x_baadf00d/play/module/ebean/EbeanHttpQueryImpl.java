/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Thibault Meyer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zero_x_baadf00d.play.module.ebean;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.zero_x_baadf00d.ebean.PlayEbeanHttpQuery;
import play.Application;
import play.Configuration;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code EbeanHttpQueryModule}.
 *
 * @author Thibault Meyer
 * @version 16.05.05
 * @see com.zero_x_baadf00d.ebean.PlayEbeanHttpQuery
 * @since 16.04.28
 */
@Singleton
public class EbeanHttpQueryImpl implements EbeanHttpQueryModule {

    /**
     * @since 16.03.09
     */
    private static final String EBEAN_HTTP_PARSER_IGNORE = "ebeanHttpQuery.ignorePatterns";

    /**
     * Handle to the Ebean HTTP Query parser.
     *
     * @since 16.04.28
     */
    private final PlayEbeanHttpQuery playEbeanHttpQuery;

    /**
     * Build a basic instance with injected dependency.
     *
     * @param configuration The current application configuration
     * @param application   The current application instance
     * @since 16.05.05
     */
    @Inject
    public EbeanHttpQueryImpl(final Configuration configuration, final Application application) {
        final List<String> patterns = configuration.getStringList(
                EbeanHttpQueryImpl.EBEAN_HTTP_PARSER_IGNORE,
                new ArrayList<>()
        );
        this.playEbeanHttpQuery = new PlayEbeanHttpQuery(application.classloader());
        this.playEbeanHttpQuery.addIgnoredPatterns(patterns);
    }

    @Override
    public <T extends Model> Query<T> buildQuery(final Class<T> c, final Http.Request request) {
        return this.playEbeanHttpQuery.buildQuery(c, request);
    }

    @Override
    public <T extends Model> Query<T> buildQuery(final Class<T> c, final Http.Request request, final Query<T> query) {
        return this.playEbeanHttpQuery.buildQuery(c, request, query);
    }
}