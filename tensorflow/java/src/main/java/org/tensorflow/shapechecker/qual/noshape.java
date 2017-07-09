package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;

/**
 * The shape of something that is not a tensor.
 */
@SubtypeOf(anyshape.class)
@ImplicitFor(literals = { LiteralKind.BOOLEAN, LiteralKind.CHAR,
		LiteralKind.DOUBLE, LiteralKind.FLOAT, LiteralKind.NULL,
		LiteralKind.STRING })
@DefaultQualifierInHierarchy
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface noshape {

}
