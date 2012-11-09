package ro.codecamp.taskdashboard.util;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class ConversionService {

    private final ImmutableMultimap<Class, FunctionWrapper> convertersMap;

    public ConversionService(List<Function> converters) {
        convertersMap = prepareConverters(converters);
    }

    @SuppressWarnings("unchecked")
    public <S, T> T convert(S source, Class<T> target) {
        ImmutableCollection<FunctionWrapper> converters = convertersMap.get(target);
        for (FunctionWrapper converter : converters) {
            if (converter.getSourceType().isAssignableFrom(source.getClass())) {
                return (T) converter.convert(source, target);
            }
        }
        throw new IllegalArgumentException("Conversion not supported from " + source.getClass() + " to " + target);
    }

    @SuppressWarnings("unchecked")
    private ImmutableMultimap<Class, FunctionWrapper> prepareConverters(List<Function> converters) {
        ImmutableMultimap.Builder<Class, FunctionWrapper> builder = ImmutableMultimap.builder();
        for (Function<?, ?> converter : converters) {
            FunctionWrapper wrapper = new FunctionWrapper(converter);
            builder.put(wrapper.getTargetType(), wrapper);
        }
        return builder.build();
    }

    private class FunctionWrapper<S, T> {
        private final Function<S, T> function;
        private final Class targetType;
        private final Class sourceType;

        private FunctionWrapper(Function<S, T> function) {
            this.function = function;
            Type[] types = getParameterizedTypeForClass(function.getClass());
            sourceType = (Class) types[0];
            targetType = (Class) types[1];
        }

        public Class getTargetType() {
            return this.targetType;
        }

        public Class getSourceType() {
            return sourceType;
        }

        private Type[] getParameterizedTypeForClass(Class aClass) {
            ParameterizedType parameterizedType = (ParameterizedType) aClass.getGenericInterfaces()[0];
            return parameterizedType.getActualTypeArguments();
        }

        public T convert(S source, Class<T> targetType) {
            return function.apply(source);
        }
    }
}
