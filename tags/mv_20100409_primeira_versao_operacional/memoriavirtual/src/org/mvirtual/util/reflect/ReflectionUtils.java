package org.mvirtual.util.reflect;

import java.lang.reflect.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtils.class);

    private static void fillTypes(Type[] toFill, Type[] tmp) {
        int i, j = 0;
        for (i = 0; i < toFill.length; i++) {
            if (!(toFill[i] instanceof Class)) {
                for (; j < tmp.length; j++) {
                    if (tmp[j] instanceof Class) {
                        toFill[i] = tmp[j++];
                        break;
                    }
                }
            }
        }
    }

    /**
     * Instantiates a new object of type InstanceType using a no-args constructor.
     * @param clazz Class instance of type InstanceType.
     * @return New instance of type InstanceType.
     * @see #instantiate(Class<InstanceType>, Class[], Object...)
     */
    public static <InstanceType> InstanceType instantiate(Class<InstanceType> clazz) {
        return instantiate(clazz, new Class[0]);
    }

    /**
     * Instantiates a new object of type InstanceType.
     * @param clazz Class instance of type InstanceType.
     * @param types Array of Class instances of InstanceType's constructor arguments types.
     * @param args Array of Object instances to pass to InstanceType's constructor.
     * @return New instance of type InstanceType.
     */
    public static <InstanceType> InstanceType instantiate(Class<InstanceType> clazz,
            Class[] types, Object... args) {
        log.debug("instantiating object of type " + clazz);
        InstanceType instance = null;
        try {
            instance = clazz.getConstructor(types).newInstance(args);
        } catch (InstantiationException e) {
            log.error("Could not instantiate object of type " + clazz + ": " + e);
            //throw e;
        } catch (IllegalAccessException e) {
            log.error("Illegal access when trying to instantiate object of type " + clazz + ": " + e);
            //throw e;
        } catch (NoSuchMethodException e) {
            log.error("Illegal access when trying to instantiate object of type " + clazz + ": " + e);
            //throw e;
        } catch (InvocationTargetException e) {
            log.error("Illegal access when trying to instantiate object of type " + clazz + ": " + e);
            //throw e;
        }
        log.debug("instantiating successful");
        return instance;
    }

    /**
     * Instantiates a new object of type defined by the String clazz using a no-args constructor.
     * @param clazz String defining the class name.
     * @return New instance of type described by clazz String.
     */
    public static Object instantiate(String clazz) {
        log.debug("instantiating object of type " + clazz);
        try {
            Class<?> c = Class.forName(clazz);
            return c.newInstance();
        } catch (ClassNotFoundException e) {
            log.error("Class not found: " + clazz);
            //throw e;
        } catch (ExceptionInInitializerError e) {
            log.error("Could not initialize class " + clazz + ": " + e);
            //throw e;
        } catch (LinkageError e) {
            log.error("Linkage error when initializing class " + clazz + ": " + e);
            //throw e;
        } catch (InstantiationException e) {
            log.error("Could not instantiate object of type " + clazz + ": " + e);
            //throw e;
        } catch (IllegalAccessException e) {
            log.error("Illegal access when trying to instantiate object of type " + clazz + ": " + e);
            //throw e;
        } catch (SecurityException e) {
            log.error("Security error when trying to instantiate object of type " + clazz + ": " + e);
            //throw e;
        }
        return null;
    }

    public static <T> Method getMethod(T o, String name, Class... parameterTypes) {
        Class<?> c = o.getClass();

        try {
            return c.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("The method named '%s' with parameters (", name));
            for (Class clazz : parameterTypes) {
                sb.append(String.format("%s, ", clazz));
            }
            sb.append(String.format(") doesn't exists in the class '%s'", c));
            log.warn(sb.toString());
        }

        return null;
    }

    public static <T> Object invokeMethod(T o, String name) {
        return invokeMethod(o, name, new Class[0]);
    }

    public static <T> Object invokeMethod(T o, String name, Class[] types, Object... args) {
        Object ret = null;

        try {
            Method m = getMethod(o, name, types);
            if (m != null) {
                ret = m.invoke(o, args);
            } else {
                throw new RuntimeException("Could not get the method named '" + name + "' on object of type '" + o.getClass() + "'");
            }
        } catch (IllegalAccessException e) {
            log.error("Illegal access when trying to invoke method '" + name + "': " + e);
            e.printStackTrace();
            //throw e;
        } catch (InvocationTargetException e) {
            log.error("Exception thrown by the method '" + name + "': " + e);
            e.printStackTrace();
            //throw e;
        }

        return ret;
    }

    public static Type[] getGenericSuperclassTypes(Class c) {
        return ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments();
    }

    public static <T> Type[] getRecursiveGenericTypes(Class<T> parent,
            Class<? extends T> child) {
        Class c = child;
        Type[] types = null;

        while (c != parent) {
            Type[] toFill = getGenericSuperclassTypes(c);
            if (types != null) {
                fillTypes(toFill, types);
            }
            types = toFill;
            c = c.getSuperclass();
        }

        return types;
    }

    public static <T> Class<?>[] getRecursiveGenericClassTypes(Class<T> parent,
            Class<? extends T> child) {
        Type[] types = getRecursiveGenericTypes(parent, child);
        Class[] classes = new Class[types.length];

        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }

        return classes;
    }

    public static <T> String getSimpleClassName(T o) {
        return (o != null ? o.getClass().getSimpleName() : "");
    }

    public static <T> String getSimpleClassName(T o, String nullString) {
        return (o != null ? o.getClass().getSimpleName() : nullString);
    }
}
