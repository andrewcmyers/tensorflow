# Description:
# TensorFlow Java API.

package(default_visibility = ["//visibility:private"])

licenses(["notice"])  # Apache 2.0

load("build_defs", "JAVACOPTS")

java_library(
    name = "tensorflow_unchecked",
    srcs = [
        ":java_op_sources",
        ":java_sources",
        ":shapechecker_sources",
        ":generated_source"
    ],
    javacopts = JAVACOPTS,
    deps = ["@checkerframework//jar"],
    visibility = ["//visibility:public"],
)

java_library(
    name = "tensorflow",
    srcs = [
        ":java_op_sources",
        ":java_sources",
        ":shapechecker_sources",
        ":generated_source"
    ],
    plugins = [":shapechecker"],
    deps = ["@checkerframework//jar"],
    exported_plugins = ["shapechecker"],
#   data = [":libtensorflow_jni"],
    javacopts = JAVACOPTS + ["-AprintErrorStack"],
    visibility = ["//visibility:public"],
)

# NOTE(ashankar): Rule to include the Java API in the Android Inference Library
# .aar. At some point, might make sense for a .aar rule here instead.
filegroup(
    name = "java_sources",
    srcs = glob(["src/main/java/org/tensorflow/*.java"]),
    visibility = [
        "//tensorflow/contrib/android:__pkg__",
        "//tensorflow/java:__pkg__",
    ],
)

filegroup(
    name = "shapechecker_sources",
    srcs = glob(["**/main/**/shapechecker/**/*.java"])
)

filegroup(
    name = "java_op_sources",
    srcs = glob(["src/main/java/org/tensorflow/op/*.java"]),
    visibility = [
        "//tensorflow/java:__pkg__",
    ],
)

java_library(
    name = "testutil",
    testonly = 1,
    srcs = ["src/test/java/org/tensorflow/TestUtil.java"],
    javacopts = JAVACOPTS,
    plugins = [":shapechecker"],
    deps = [":tensorflow"],
)

java_test(
    name = "GraphTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/GraphTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.GraphTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "OperationBuilderTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/OperationBuilderTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.OperationBuilderTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "OperationTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/OperationTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.OperationTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "SavedModelBundleTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/SavedModelBundleTest.java"],
    data = ["//tensorflow/cc/saved_model:saved_model_half_plus_two"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.SavedModelBundleTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "SessionTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/SessionTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.SessionTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "ShapeTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/ShapeTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.ShapeTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "TensorFlowTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/TensorFlowTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.TensorFlowTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        "@junit",
    ],
)

java_test(
    name = "TensorTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/TensorTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.TensorTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

java_test(
    name = "ScopeTest",
    size = "small",
    srcs = ["src/test/java/org/tensorflow/op/ScopeTest.java"],
    javacopts = JAVACOPTS,
    test_class = "org.tensorflow.op.ScopeTest",
    plugins = [":shapechecker"],
    deps = [
        ":tensorflow",
        ":testutil",
        "@junit",
    ],
)

filegroup(
    name = "libtensorflow_jni",
    srcs = select({
        "//tensorflow:darwin": [":libtensorflow_jni.dylib"],
        "//conditions:default": [":libtensorflow_jni.so"],
    }),
    visibility = ["//visibility:public"],
)

LINKER_VERSION_SCRIPT = ":config/version_script.lds"

LINKER_EXPORTED_SYMBOLS = ":config/exported_symbols.lds"

cc_binary(
    name = "libtensorflow_jni.so",
    # Set linker options to strip out anything except the JNI
    # symbols from the library. This reduces the size of the library
    # considerably (~50% as of January 2017).
    linkopts = select({
        "//tensorflow:debug": [],  # Disable all custom linker options in debug mode
        "//tensorflow:darwin": [
            "-Wl,-exported_symbols_list",  # This line must be directly followed by LINKER_EXPORTED_SYMBOLS
            LINKER_EXPORTED_SYMBOLS,
        ],
        "//tensorflow:windows": [],
        "//tensorflow:windows_msvc": [],
        "//conditions:default": [
            "-z defs",
            "-s",
            "-Wl,--version-script",  #  This line must be directly followed by LINKER_VERSION_SCRIPT
            LINKER_VERSION_SCRIPT,
        ],
    }),
    linkshared = 1,
    linkstatic = 1,
    deps = [
        "//tensorflow/java/src/main/native",
        LINKER_VERSION_SCRIPT,
        LINKER_EXPORTED_SYMBOLS,
    ],
)

genrule(
    name = "pom",
    outs = ["pom.xml"],
    cmd = "$(location generate_pom) >$@",
    output_to_bindir = 1,
    tools = [":generate_pom"],
)

cc_binary(
    name = "generate_pom",
    srcs = ["generate_pom.cc"],
    deps = ["//tensorflow/c:c_api"],
)

# System.loadLibrary() on OS X looks for ".dylib" or ".jnilib"
# and no ".so". If and when https://github.com/bazelbuild/bazel/issues/914
# is resolved, perhaps this workaround rule can be removed.
genrule(
    name = "darwin-compat",
    srcs = [":libtensorflow_jni.so"],
    outs = ["libtensorflow_jni.dylib"],
    cmd = "cp $< $@",
    output_to_bindir = 1,
)

filegroup(
    name = "all_files",
    srcs = glob(
        ["**/*"],
        exclude = [
            "**/METADATA",
            "**/OWNERS",
        ],
    ),
    visibility = ["//tensorflow:__subpackages__"],
)

filegroup(
    name = "generated_source",
    srcs = [ "tensorflow/java/src/main/java/org/tensorflow/Types.java",
             "tensorflow/java/src/main/java/org/tensorflow/Tensor.java",
             "tensorflow/java/src/main/java/org/tensorflow/op/Tensors.java" ]
)

genrule(
    name = "create_Types",
    srcs = glob(["**/Types.java.tmpl", "**/tftypes.csv", "**/tftypes.pl"]),
    outs = ["tensorflow/java/src/main/java/org/tensorflow/Types.java"],
    cmd = "echo '...building $@' 2>&1; perl tensorflow/java/src/gen/perl/tftypes.pl -t tensorflow/java/src/gen/resources/tftypes.csv tensorflow/java/src/gen/resources/Types.java.tmpl > \"$@\""
)

genrule(
    name = "create_Tensor",
    srcs = glob(["**/Tensor.java.tmpl", "**/tftypes.csv", "**/tftypes.pl"]),
    outs = ["tensorflow/java/src/main/java/org/tensorflow/Tensor.java"],
    cmd = "echo '...building $@' 2>&1; perl tensorflow/java/src/gen/perl/tftypes.pl -T tensorflow/java/src/gen/resources/tftypes.csv tensorflow/java/src/gen/resources/Tensor.java.tmpl > \"$@\"",
)

genrule(
    name = "create_Tensors",
    srcs = glob(["**/Tensors.java.tmpl", "**/tftypes.csv", "**/tftypes.pl"]),
    outs = ["tensorflow/java/src/main/java/org/tensorflow/op/Tensors.java"],
    cmd = "echo '...building $@' 2>&1; perl tensorflow/java/src/gen/perl/tftypes.pl -c tensorflow/java/src/gen/resources/tftypes.csv tensorflow/java/src/gen/resources/Tensors.java.tmpl > \"$@\"",
)

java_plugin(
     name = "shapechecker",
     processor_class = "org.tensorflow.shapechecker.ShapeChecker",
    # srcs = [ ":shapechecker_sources" ],
     deps = ["@checkerframework//jar", ":tensorflow_unchecked"],
)
