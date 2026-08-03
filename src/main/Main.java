package main;

import EscapeHouse.SistemaEscapeHouse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaEscapeHouse sistema = new SistemaEscapeHouse();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
           
            System.out.println("1. Carga inicial");
            System.out.println("2. ABM (Habitaciones, Desafíos, Equipos)");
            System.out.println("3. Consultas sobre Habitaciones");
            System.out.println("4. Consultas sobre Desafíos");
            System.out.println("5. Consultas sobre Equipos");
            System.out.println("6. Consulta General");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                   
                    sistema.cargarDatosDesdeArchivo("datianos/cargadatos.txt");
                    break;
                case 6:
                    sistema.mostrarSistema();
                    break;
                case 0:
                    sistema.escribirLog("Ejecución finalizada por el usuario.");
                    System.out.println("¡Gracias por jugar a Escape House!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}