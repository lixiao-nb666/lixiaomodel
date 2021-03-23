package com.lixiao.build.util;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 反射操作工具
 * 
 * @author eguid
 * 
 */
public class ReflectUtil {

	public static final String SET = "set";
	public static final String GET = "get";
	public static final String IS = "is";

	public static Object mapToObj(Map<String, Object> map, Class<?> oc)
			throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
		Method[] ms = oc.getDeclaredMethods();
		if (ms == null || ms.length < 1) {
			return null;
		}
		Object obj = getObject(oc);
		for (Method m : ms) {
			String methodName = m.getName();
			String fieldName = getMethodField(methodName, SET);
			Object value = map.get(fieldName);
			if (value != null) {
				setMethodValue(m, obj, typeConvert(value, m));
			}
		}
		return obj;
	}

	public static Object typeConvert(Object obj, Method m) {
		return typeConvert(obj, m.getParameterTypes()[0].getName());
	}

	public static Object typeConvert(Object obj, Field f) {
		return typeConvert(obj, f.getType().getName());
	}

	/**
	 * 基础数据转换
	 * 
	 * @param obj
	 * @param typeName
	 * @return
	 */
	public static Object typeConvert(Object obj, String typeName) {
		// 基础数据都可以转为String
		String str = String.valueOf(obj);
		if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
			return Integer.valueOf(str.trim());
		} else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
			return Long.valueOf(str.trim());
		} else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
			return Byte.valueOf(str.trim());
		} else if ("short".equals(typeName)
				|| "java.lang.Short".equals(typeName)) {
			return Short.valueOf(str.trim());
		} else if ("float".equals(typeName)
				|| "java.lang.Float".equals(typeName)) {
			return Float.valueOf(str.trim());
		} else if ("double".equals(typeName)
				|| "java.lang.Double".equals(typeName)) {
			return Double.valueOf(str.trim());
		} else if ("boolean".equals(typeName)
				|| "java.lang.Boolean".equals(typeName)) {
			return CommonUtil.TRUE.equals(str) ? true : false;
		} else if ("char".equals(typeName)
				|| "java.lang.Character".equals(typeName)) {
			return Character.valueOf(str.trim().charAt(0));
		} else if ("java.lang.String".equals(typeName)) {
			return str;
		}
		return null;
	}


	public static boolean isConvert(String typeName) {
		// 基础数据都可以转为String

		if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
			return true;
		} else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
			return true;
		} else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
			return true;
		} else if ("short".equals(typeName)
				|| "java.lang.Short".equals(typeName)) {
			return true;
		} else if ("float".equals(typeName)
				|| "java.lang.Float".equals(typeName)) {
			return true;
		} else if ("double".equals(typeName)
				|| "java.lang.Double".equals(typeName)) {
			return true;
		} else if ("boolean".equals(typeName)
				|| "java.lang.Boolean".equals(typeName)) {
			return true;
		} else if ("char".equals(typeName)
				|| "java.lang.Character".equals(typeName)) {
			return true;
		} else if ("java.lang.String".equals(typeName)) {
			return true;
		}
		return false;
	}


	/**
	 * 基础数据转换
	 *
	 * @param str
	 * @param typeName
	 * @return
	 */
	public static Object typeConvert(String str, String typeName) {
		// 基础数据都可以转为String
		if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
			return Integer.valueOf(str.trim());
		} else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
			return Long.valueOf(str.trim());
		} else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
			return Byte.valueOf(str.trim());
		} else if ("short".equals(typeName)
				|| "java.lang.Short".equals(typeName)) {
			return Short.valueOf(str.trim());
		} else if ("float".equals(typeName)
				|| "java.lang.Float".equals(typeName)) {
			return Float.valueOf(str.trim());
		} else if ("double".equals(typeName)
				|| "java.lang.Double".equals(typeName)) {
			return Double.valueOf(str.trim());
		} else if ("boolean".equals(typeName)
				|| "java.lang.Boolean".equals(typeName)) {
			return CommonUtil.TRUE.equals(str) ? true : false;
		} else if ("char".equals(typeName)
				|| "java.lang.Character".equals(typeName)) {
			return Character.valueOf(str.trim().charAt(0));
		} else if ("java.lang.String".equals(typeName)) {
			return str;
		}
		return null;
	}

	public static Class<?> getFieldType(Class<?> cl, String fieldName)
			throws NoSuchFieldException, SecurityException {
		Field f = cl.getDeclaredField(fieldName);
		return f.getType();
	}

	public static Field findField(Class<?> cl, String fieldName)
			throws NoSuchFieldException, SecurityException {
		return cl.getDeclaredField(fieldName);
	}

	/**
	 * 执行方法
	 * 
	 * @param m
	 *            - 方法
	 * @param obj
	 *            - 对象
	 * @param value
	 *            - 参数
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object setMethodValue(Method m, Object obj, Object... value)
			throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
		m.getParameterTypes();
		return m.invoke(obj, value);
	}

	public static Object getFieldValue(Class<?> obj, String FieldName)
			throws NoSuchFieldException, SecurityException {
		return obj.getDeclaredField(FieldName);
	}

	/**
	 * 通过class实例化
	 * 
	 * @param oc
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object getObject(Class<?> oc) throws InstantiationException,
            IllegalAccessException {
		return oc.newInstance();
	}

	/**
	 * 获取方法字段
	 * 
	 * @param methodName
	 * @param prefix
	 * @return
	 */
	public static String getMethodField(String methodName, String prefix) {
		String m = null;
		if (prefix != null) {
			if (methodName.indexOf(prefix) >= 0) {
				m = methodName.substring(prefix.length());
				return stringFirstLower(m);
			}
		}
		return m;
	}

	/**
	 * 检测一个方法是否是SET方法
	 * 
	 * @param methodName
	 * @param fieldName
	 * @return
	 */
	public static boolean checkMethodIsSetByField(String methodName,
                                                  String fieldName) {
		if (TextUtils.isEmpty(methodName) || TextUtils.isEmpty(fieldName)) {
			return false;
		}
		if (methodName.indexOf(SET) >= 0) {
			// 判断是否是一个，field的SET方法，必须前面要有set这个字段
			String methodFieldName = methodName.substring(SET.length());
			if (stringFirstLower(methodFieldName).equals(fieldName)) {
				return true;
			}
			if (fieldName.indexOf(IS) >= 0) {
				String fieldName1 = stringFirstLower(fieldName.substring(IS
						.length()));
				if (stringFirstLower(methodFieldName).equals(fieldName1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检测一个方法是否是GET方法
	 * 
	 * @param methodName
	 * @param fieldName
	 * @return
	 */
	public static boolean checkMethodIsGetByField(String methodName,
                                                  String fieldName) {
		if (TextUtils.isEmpty(methodName) || TextUtils.isEmpty(fieldName)) {
			return false;
		}
		if (methodName.equals(fieldName)) {
			//方法名字和FIELD一样就是GET方法
			return true;
		}
		if (methodName.indexOf(GET) >= 0) {
			// 方法名前面是GET去掉GET后一样也是GET方法
			String methodFieldName = methodName.substring(GET.length());
			if (stringFirstLower(methodFieldName).equals(fieldName)) {
				return true;
			}
		}
		if (methodName.indexOf(IS) >= 0) {
			// 方法名前面是IS去掉IS后一样也是GET方法
			String methodFieldName = methodName.substring(IS.length());
			if (stringFirstLower(methodFieldName).equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFirstUpper(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFirstLower(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}
	//
	// public static void showType(Class obj) {
	// for (Field f : obj.getDeclaredFields()) {
	// System.err.println(f.getType().getName());
	// }
	// }

}
