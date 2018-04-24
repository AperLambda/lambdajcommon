/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

module org.aperlambda.lambdacommon {
    exports org.aperlambda.lambdacommon.config;
    exports org.aperlambda.lambdacommon.documents;
    exports org.aperlambda.lambdacommon.resources;
    exports org.aperlambda.lambdacommon.system;
    opens org.aperlambda.lambdacommon.utils;
    exports org.aperlambda.lambdacommon.utils.function;

    requires java.desktop;

    requires annotations;
    requires com.google.common;
    requires gson;
}