package org.tensorflow.shapechecker.qual;

import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf(noshape.class)
@ImplicitFor(literals = {LiteralKind.INT, LiteralKind.LONG})
public @interface noshapeConstInt {

}
