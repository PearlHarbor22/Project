package com.bank.profile.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, указывающая, что метод или класс подлежит аудиту.
 * Используется в связке с {@link AuditAspect} для перехвата операций create*, update* и других.
 * Применяется к методам или классам, где необходимо сохранять действия в аудит.
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
}
