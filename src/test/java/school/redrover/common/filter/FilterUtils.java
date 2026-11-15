package school.redrover.common.filter;

import org.testng.IClass;
import org.testng.IMethodInstance;
import org.testng.ITestNGMethod;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterUtils {

    public static List<IMethodInstance> filterMethods(List<String> fileList, String dependenciesClasses, List<IMethodInstance> methodList) {
        final String pathTemplate = "src/test/java/%s.java";

        Set<String> changedFiles = fileList.stream()
                .filter(e -> !e.startsWith("D="))
                .map(e -> e.substring(e.lastIndexOf('=') + 1))
                .collect(Collectors.toSet());

        Map<Class<?>, String> classMap = methodList.stream()
                .map(IMethodInstance::getMethod).map(ITestNGMethod::getTestClass).map(IClass::getRealClass)
                .collect(Collectors.toMap(
                        Function.identity(),
                        clazz -> String.format(pathTemplate, clazz.getName().replace('.', '/')),
                        (pathA, pathB) -> pathA
                ));

        Set<String> testFiles = new HashSet<>(classMap.values());

        Map<String, Set<String>> reversedGraph =
                Arrays.stream(dependenciesClasses.split(";"))
                        .filter(s -> s.contains("="))
                        .map(s -> s.split("="))
                        .collect(Collectors.groupingBy(
                                p -> String.format(pathTemplate, p[0].replace('.', '/')),
                                Collectors.mapping(
                                        p -> String.format(pathTemplate, p[1].replace('.', '/')),
                                        Collectors.toSet()
                                )
                        ));

        Set<String> affectedFiles = new HashSet<>();

        for (String changedFile : changedFiles) {
            Set<String> visitedFiles = new HashSet<>();
            Deque<String> pendingFiles = new ArrayDeque<>(List.of(changedFile));

            while (!pendingFiles.isEmpty()) {
                String file = pendingFiles.removeFirst();
                if (visitedFiles.add(file)) {
                    pendingFiles.addAll(reversedGraph.getOrDefault(file, Collections.emptySet()));
                }
            }

            Set<String> affectedTests = visitedFiles.stream()
                    .filter(testFiles::contains)
                    .collect(Collectors.toSet());

            if (!affectedTests.isEmpty()) {
                affectedFiles.addAll(affectedTests);
            } else {
                affectedFiles.add(changedFile);
            }
        }

        if (classMap.values().containsAll(affectedFiles)) {
            return methodList.stream().filter(method -> affectedFiles.contains(classMap.get(method.getMethod().getTestClass().getRealClass()))).collect(Collectors.toList());
        }

        return methodList;
    }
}
