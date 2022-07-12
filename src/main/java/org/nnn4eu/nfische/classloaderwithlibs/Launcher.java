package org.nnn4eu.nfische.classloaderwithlibs;

import java.util.Arrays;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        List<String> filters= Arrays.asList("org.nnn4eu.nfische", "java.util.Random");
        test(filters);

        //printClassesLoadedBy("BOOTSTRAP");
        //printClassesLoadedBy("SYSTEM");
        //printClassesLoadedBy("EXTENSION");
        //printClassesLoadedBy("PLATFORM");
    }

    public static void test(List<String> filters) {
        System.out.println("test class not used and is not loaded==================");
        printClassesLoadedFilteredBy(filters);
        System.out.println("\ntest class declared and is not loaded but Random is loaded!!!!!==================");
        TestClass tc;
        printClassesLoadedFilteredBy(filters);

        System.out.println("\ntest class instantiated and loaded!!==================");
        tc=new TestClass();
        printClassesLoadedFilteredBy(filters);

        System.out.println("\ninvoking start() on test class instance==================");
        tc.start("upi");
        printClassesLoadedFilteredBy(filters);
    }

    private static void printClassesLoadedFilteredBy(List<String> filters){
        printClassesLoadedByAndFilteredBy("BOOTSTRAP",filters);
        printClassesLoadedByAndFilteredBy("SYSTEM",filters);
        //printClassesLoadedByAndFilteredBy("EXTENSION",filters);
        //printClassesLoadedByAndFilteredBy("PLATFORM",filters);
    }
    private static void printClassesLoadedByAndFilteredBy(String classLoaderType, List<String> filters) {
        //System.out.println("================================================");
        System.out.println(classLoaderType + " ClassLoader");
        System.out.println("------------------------------------------------");
        Class<?>[] classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
        Arrays.asList(classes)
                .stream()
                .map(clazz->clazz.getCanonicalName())
                .filter(name -> name!=null)
                .sorted()
                .filter(name -> filters.stream().parallel().anyMatch(c-> name.contains(c)) )
                .forEach(name -> System.out.println(name));
        System.out.println("------------------------------------------------");
    }
    private static void printClassesLoadedBy(String classLoaderType) {
        System.out.println("================================================");
        System.out.println(classLoaderType + " ClassLoader");

        Class<?>[] classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
        Arrays.asList(classes)
                .forEach(clazz -> System.out.println(clazz.getCanonicalName()));
        System.out.println("================================================\n\n");
    }
}

//https://www.baeldung.com/java-instrumentation#loading-a-java-agent
//https://www.baeldung.com/java-list-classes-class-loader

//java -javaagent:target/classloading-test-1.0-SNAPSHOT.jar -jar target/classloading-test-1.0-SNAPSHOT.jar