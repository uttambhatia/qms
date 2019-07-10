package com.cs.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.cs.demo.pojo.DataSource;
import com.google.gson.Gson;

public class TestCCLoader {

	public static void main(String[] args) throws Exception {

		/*
		Class<?> c1 = new TestClassLoader1().loadClass("TestCCLoader");
        Class<?> c2 = new TestClassLoader1().loadClass("TestCCLoader");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c1 == c2);
		 */
		
		CustomClassLoaderDemo loader = new CustomClassLoaderDemo();
        Class<?> c = loader.findClass("com.cs.demo.pojo.DataSource");
        Object obj = c.newInstance();
        
        System.out.println("Default : "+DataSource.class.getClassLoader());
		System.out.println("Before cast : "+obj.getClass().getClassLoader());
		
		DataSource ms = castObj(obj);
		System.out.println("After cast : "+ms.getClass().getClassLoader());
		
		Gson gson = new Gson();
		DataSource yo = gson.fromJson(gson.toJson(obj), DataSource.class);
		
		System.out.println("Gson cast : "+yo.getClass().getClassLoader());
		
		/*
		addPath("D:\\Vannilla\\demo\\target\\classes");
	    Class clazz = ClassLoader.getSystemClassLoader().loadClass("com.cs.demo.pojo.DataSource");
	    System.out.println(clazz.getClassLoader());
	    Object car = clazz.newInstance();
	    System.out.println(clazz.getMethod("getUrl").invoke(car));*/
       
	}
	
	
	
	private static <T> T castObj(Object o) throws IOException, ClassNotFoundException {
	    if (o != null) {
	        ByteArrayOutputStream baous = new ByteArrayOutputStream();
	        {
	            ObjectOutputStream oos = new ObjectOutputStream(baous);
	            try {
	                oos.writeObject(o);
	            } finally {
	                try {
	                    oos.close();
	                } catch (Exception e) {
	                }
	            }
	        }

	        byte[] bb = baous.toByteArray();
	        if (bb != null && bb.length > 0) {
	            ByteArrayInputStream bais = new ByteArrayInputStream(bb);
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            T res = (T) ois.readObject();
	            return res;
	        }
	    }
	    return null;
	}
	
	public static void addPath(String s) throws Exception {
	    File f=new File(s);
	    URL u=f.toURI().toURL();
	    URLClassLoader urlClassLoader=(URLClassLoader)ClassLoader.getSystemClassLoader();
	    Class urlClass=URLClassLoader.class;
	    Method method=urlClass.getDeclaredMethod("addURL",new Class[]{URL.class});
	    method.setAccessible(true);
	    method.invoke(urlClassLoader,new Object[]{u});
	}
	
	static class TestClassLoader1 extends ClassLoader {

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (!name.equals("TestCCLoader")) {
                return super.loadClass(name);
            }
            
            InputStream in = null;
    		try {
    			// get input stream linked to this class
    			addPath("D:\\Vannilla\\demo\\target\\classes");
    			in = ClassLoader.getSystemResourceAsStream("DataSource.class");
    			int data = in.read();
    			// initialize buffer
    			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    			// loop over data till more data is available
    			while (data != -1) {
    				// write data to buffer
    				buffer.write(data);
    				data = in.read();
    			}
    			// convert to byte array as this is the format accepted by
    			// defineClass method
    			byte[] classData = buffer.toByteArray();
    			// converts a byte array to a instance of class java.lang.Class
    			return defineClass(name, classData, 0, classData.length);
    		} catch (Exception e) {
    			throw new ClassNotFoundException();
    		} finally {
    			if (in != null) {
    				try {
    					in.close();
    				} catch (IOException e) {
    					// ignore
    				}
    			}
    		}
        }
    }

}
