import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;

public class LibretaDeNotas {
    private HashMap<String, ArrayList<Double>> calificaciones;
    private Scanner scanner;
    private static final double NOTA_APROBACION = 4.0; // Puedes ajustar esto si es necesario

    public LibretaDeNotas() {
        this.calificaciones = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LibretaDeNotas libreta = new LibretaDeNotas();
        libreta.ingresarCalificaciones();
        libreta.mostrarMenu();
    }

    // --- Ingresar Calificaciones ---
    public void ingresarCalificaciones() {
        int cantidadAlumnos = 0;
        int cantidadNotasPorAlumno = 0;

        while (cantidadAlumnos <= 0) {
            try {
                System.out.print("Ingrese la cantidad de alumnos: ");
                cantidadAlumnos = scanner.nextInt();
                if (cantidadAlumnos <= 0) {
                    System.out.print("La cantidad de alumnos debe ser un número positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Por favor, ingrese un número entero.");
                scanner.next(); // Limpiar buffer
            }
        }

        while (cantidadNotasPorAlumno <= 0) {
            try {
                System.out.print("Ingrese la cantidad de notas por alumno: ");
                cantidadNotasPorAlumno = scanner.nextInt();
                if (cantidadNotasPorAlumno <= 0) {
                    System.out.print("La cantidad de notas debe ser un número positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Por favor, ingrese un número entero.");
                scanner.next(); // Limpiar buffer
            }
        }

        scanner.nextLine(); // Consumir salto de línea

        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.print("Ingrese el nombre del alumno " + (i + 1) + ": ");
            String nombreAlumno = scanner.nextLine();

            ArrayList<Double> notas = new ArrayList<>();

            for (int j = 0; j < cantidadNotasPorAlumno; j++) {
                double nota = -1.0;
                while (nota < 1.0 || nota > 7.0) {
                    try {
                        System.out.print("Ingrese la nota " + (j + 1) + " para " + nombreAlumno + " (1.0-7.0): ");
                        nota = scanner.nextDouble();
                        if (nota < 1.0 || nota > 7.0) {
                            System.out.print("Nota inválida. La nota debe estar entre 1.0 y 7.0.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("Entrada inválida. Por favor, ingrese un número decimal.");
                        scanner.next(); // Limpiar buffer
                    }
                }
                notas.add(nota);
            }

            calificaciones.put(nombreAlumno, notas);
            scanner.nextLine(); // Consumir salto de línea
        }
    }

    // --- Calcular promedio ---
    private double calcularPromedio(ArrayList<Double> notas) {
        if (notas.isEmpty()) return 0.0;
        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    // --- Mostrar estadísticas por estudiante ---
    public void calcularYMostrarEstadisticasPorEstudiante() {
        System.out.print("\n--- Estadísticas de Calificaciones por Estudiante ---");
        if (calificaciones.isEmpty()) {
            System.out.print("\nNo hay calificaciones ingresadas.");
            return;
        }

        for (Map.Entry<String, ArrayList<Double>> entrada : calificaciones.entrySet()) {
            String nombre = entrada.getKey();
            ArrayList<Double> notas = entrada.getValue();
            double promedio = calcularPromedio(notas);
            double max = Collections.max(notas);
            double min = Collections.min(notas);

            System.out.printf("\nAlumno: %s", nombre);
            System.out.printf("\n  Promedio: %.2f", promedio);
            System.out.printf("\n  Nota Máxima: %.2f", max);
            System.out.printf("\n  Nota Mínima: %.2f", min);
            System.out.print("\n---");
        }
    }

    // --- Menú Principal ---
    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.print("\n\n--- Menú de Opciones ---");
            System.out.print("\n1. Mostrar el Promedio de Notas por Estudiante.");
            System.out.print("\n2. Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante.");
            System.out.print("\n3. Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante.");
            System.out.print("\n0. Salir del Menú.");
            System.out.print("\nSeleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea

                switch (opcion) {
                    case 1:
                        mostrarPromedioNotasPorEstudiante();
                        break;
                    case 2:
                        verificarNotaAprobatoria();
                        break;
                    case 3:
                        verificarNotaRespectoPromedioCurso();
                        break;
                    case 0:
                        System.out.print("\nSaliendo del programa. ¡Hasta luego!");
                        break;
                    default:
                        System.out.print("\nOpción inválida. Por favor, intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.print("\nEntrada inválida. Por favor, ingrese un número entero.");
                scanner.next(); // Limpiar buffer
            }
        }
        scanner.close();
    }

    // --- Opción 1: Mostrar Promedio ---
    public void mostrarPromedioNotasPorEstudiante() {
        System.out.print("\n--- Promedio de Notas por Estudiante ---");
        if (calificaciones.isEmpty()) {
            System.out.print("\nNo hay calificaciones ingresadas.");
            return;
        }

        for (Map.Entry<String, ArrayList<Double>> entrada : calificaciones.entrySet()) {
            String nombre = entrada.getKey();
            ArrayList<Double> notas = entrada.getValue();
            double promedio = calcularPromedio(notas);
            System.out.printf("\nAlumno: %s - Promedio: %.2f", nombre, promedio);
        }
    }

    // --- Opción 2: Verificar aprobación ---
    public void verificarNotaAprobatoria() {
        if (calificaciones.isEmpty()) {
            System.out.print("\nNo hay calificaciones ingresadas.");
            return;
        }

        System.out.print("\nIngrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!calificaciones.containsKey(nombre)) {
            System.out.print("\nEl estudiante '" + nombre + "' no se encuentra en la lista.");
            return;
        }

        double nota = -1;
        while (nota < 1.0 || nota > 7.0) {
            try {
                System.out.print("\nIngrese la nota a verificar (1.0-7.0): ");
                nota = scanner.nextDouble();
                if (nota < 1.0 || nota > 7.0) {
                    System.out.print("\nNota inválida. La nota debe estar entre 1.0 y 7.0.");
                }
            } catch (InputMismatchException e) {
                System.out.print("\nEntrada inválida. Por favor, ingrese un número decimal.");
                scanner.next();
            }
        }
        scanner.nextLine(); // Consumir salto de línea

        if (nota >= NOTA_APROBACION) {
            System.out.printf("\nLa nota %.2f para %s es APROBATORIA.", nota, nombre);
        } else {
            System.out.printf("\nLa nota %.2f para %s es REPROBATORIA.", nota, nombre);
        }
    }

    // --- Opción 3: Comparar contra promedio del curso ---
    public void verificarNotaRespectoPromedioCurso() {
        if (calificaciones.isEmpty()) {
            System.out.print("\nNo hay calificaciones ingresadas.");
            return;
        }

        System.out.print("\nIngrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!calificaciones.containsKey(nombre)) {
            System.out.print("\nEl estudiante '" + nombre + "' no se encuentra en la lista.");
            return;
        }

        double nota = -1;
        while (nota < 1.0 || nota > 7.0) {
            try {
                System.out.print("\nIngrese la nota a verificar (1.0-7.0): ");
                nota = scanner.nextDouble();
                if (nota < 1.0 || nota > 7.0) {
                    System.out.print("\nNota inválida. La nota debe estar entre 1.0 y 7.0.");
                }
            } catch (InputMismatchException e) {
                System.out.print("\nEntrada inválida. Por favor, ingrese un número decimal.");
                scanner.next();
            }
        }
        scanner.nextLine(); // Consumir salto de línea

        double promedioGeneral = calcularPromedioGeneralCurso();
        System.out.printf("\nEl promedio general del curso es: %.2f", promedioGeneral);

        if (nota > promedioGeneral) {
            System.out.printf("\nLa nota %.2f para %s está POR SOBRE el promedio del curso.", nota, nombre);
        } else if (nota < promedioGeneral) {
            System.out.printf("\nLa nota %.2f para %s está POR DEBAJO del promedio del curso.", nota, nombre);
        } else {
            System.out.printf("\nLa nota %.2f para %s es IGUAL al promedio del curso.", nota, nombre);
        }
    }

    // --- Calcular promedio general del curso ---
    private double calcularPromedioGeneralCurso() {
        double total = 0;
        int count = 0;
        for (ArrayList<Double> notas : calificaciones.values()) {
            for (double nota : notas) {
                total += nota;
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }
}