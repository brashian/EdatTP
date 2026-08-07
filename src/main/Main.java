package main;

import EscapeHouse.Desafio;
import EscapeHouse.Equipo;
import EscapeHouse.Habitacion;
import EscapeHouse.SistemaEscapeHouse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaEscapeHouse sistema = new SistemaEscapeHouse();
        Scanner sc = new Scanner(System.in);
        int opcion;

        //crear log ? o log en blanco
        sistema.crearLog();

        do {
        
            System.out.println("1. Carga inicial");
            System.out.println("2. ABM (Habitaciones, Desafíos, Equipos)");
            System.out.println("3. Consultas sobre Habitaciones");
            System.out.println("4. Consultas sobre Desafios");
            System.out.println("5. Consultas sobre Equipos");
            System.out.println("6. Consulta Gral");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    sistema.cargarDatosDesdeArchivo("datianos/cargadatos.txt");
                    break;
                case 2:
                    menuABM(sistema, sc);
                    break;
                case 3:
                    menuHabitaciones(sistema, sc);
                    break;
                case 4:
                    menuDesafios(sistema, sc);
                    break;
                case 5:
                    menuEquipos(sistema, sc);
                    break;
                case 6:
                    System.out.println(sistema.mostrarSistema());
                    break;
                case 0:
                    sistema.escribirLog("Fin de la ejecución");
                    System.out.println("¡Gracias por jugar a Escape House!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    //submenus
    private static void menuABM(SistemaEscapeHouse sistema, Scanner sc){
        int opc;
            do{
            System.out.println("\n--- SUBMENÚ ABM ---");
            System.out.println("1. Alta Habi");
            System.out.println("2. Baja Habi");
            System.out.println("3. Modificar Habi");
            System.out.println("4. Alta Desaf");
            System.out.println("5. Baja Desaf");
            System.out.println("6. Modificar Desaf");
            System.out.println("7. Alta Equipo");
            System.out.println("8. Baja Equipo");
            System.out.println("9. Modificar Equipo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion ");
            
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    System.out.print("Meta Codigo: "); 
                    String codHab = sc.nextLine();
                    System.out.print("Nombre: "); 
                    String nomHab = sc.nextLine();
                    System.out.print("Planta: "); 
                    int planta = sc.nextInt();
                    System.out.print("Metros Cuadrados: "); 
                    double m2 = sc.nextDouble();
                    System.out.print("¿Tiene salida? (true/false): "); 
                    boolean salida = sc.nextBoolean();
                    System.out.print("¿Es entrada? (true/false): "); 
                    boolean entrada = sc.nextBoolean();
                    sc.nextLine();
                    
                    Habitacion nuevaHab = new Habitacion(codHab, nomHab, planta, m2, salida, entrada);
                    if(sistema.altaHabitacion(nuevaHab)) {
                        System.out.println("Habitación dada de alta.");
                    } else {
                        System.out.println("No se pudo dar de alta. solo  alta a hab intermedias.");
                    }
                    break;

                case 2:
                    System.out.print("Codigo de la habitacion a eliminar: "); 
                    String codBajaHab = sc.nextLine();
                    if(sistema.bajaHabitacion(codBajaHab)){
                    System.out.println("Eliminada");
                    }else{
                        System.out.println("No existe o es una entrada/salida");
                    }
                    

                    break;

                case 3:
                    System.out.print("Codigo de la habitacion a modificar: "); 
                    String codModHab = sc.nextLine();
                    //saber cual modifico- no cambiar codigo hab
                    System.out.print("Nuevo Nombre: "); 
                    String nuevoNom = sc.nextLine();
                    System.out.print("Nueva Planta: "); 
                    int nuevaPlanta = sc.nextInt();
                    System.out.print("Nuevos Metros Cuadrados: "); 
                    double nuevosM2 = sc.nextDouble();
                    sc.nextLine();
                    if(sistema.modificarHabitacion(codModHab, nuevoNom, nuevaPlanta, nuevosM2)){
                         System.out.println("Habitacion modificada .");
                    }else{
                        System.out.println("No se pudo modificar.");
                    }
                       
                     
                    break;

                case 4:
                    System.out.print("Codigo Habitacion Para Desafio: "); 
                    String codHabDes = sc.nextLine();
                    System.out.print("Puntaje Desafio"); 
                    int puntajeDes = sc.nextInt();
                    sc.nextLine();


                    System.out.print("Nombre del Desafio "); 
                    String nomDes = sc.nextLine();
                    System.out.print("Tipo"); 
                    String tipoDes = sc.nextLine();
                    
                    Desafio nuevoDesafio = new Desafio(puntajeDes, nomDes, tipoDes);
                    if(sistema.altaDesafio(codHabDes, nuevoDesafio)) {
                        System.out.println("Desaf creado.");
                    }else{
                        System.out.println("Error al crear. Verifique si la habitación existe.");
                    };

                    break;

                case 5:
                    System.out.print("Codigo Habitacion: "); 
                    String codHabBaja = sc.nextLine();
                    System.out.print("Pts Desafio a eliminar: "); 
                    int puntajeBaja = sc.nextInt();
                    sc.nextLine();
                    if(sistema.bajaDesafio(codHabBaja, puntajeBaja)) {
                        System.out.println("Desafío eliminado.");
                    }else {
                        System.out.println("No se encontró el desafio o habitación.");};
                    break;

                case 6:
                    System.out.print("Codigo de Habitacion: "); 
                    String codHabMod = sc.nextLine();
                    System.out.print("Pts desaf a modificar "); 
                    int puntajeMod = sc.nextInt();
                    sc.nextLine();



                    System.out.print("Nuevo Nombre: "); 
                    String nuevoNomDes = sc.nextLine();
                    System.out.print("Nuevo Tipo: "); 
                    String nuevoTipoDes = sc.nextLine();
                    
                    if(sistema.modificarDesafio(codHabMod, puntajeMod, nuevoNomDes, nuevoTipoDes)){
                        System.out.println("Desafío modificado.");}
                    else {
                        System.out.println("No se pudo modificar.");}
                    break;

                case 7:
                    System.out.print("Nombre Equipo: "); 
                    String nomEq = sc.nextLine();
                    System.out.print("Puntaje exigido para salir: ");
                    int ptsExigidos = sc.nextInt();
                    System.out.print("Puntaje total acumulado: "); 
                    int ptsTotal = sc.nextInt();
                    sc.nextLine();


                    System.out.print("Habitacion actual: "); 
                    String habActual = sc.nextLine();
                    System.out.print("Puntaje actual en habitacion: "); 
                    int ptsHab = sc.nextInt();
                    sc.nextLine();
                    
                    Equipo nuevoEq = new Equipo(nomEq, ptsExigidos, ptsTotal, habActual, ptsHab);
                    if(sistema.altaEquipo(nuevoEq)) {
                        System.out.println("Equipo creado exitosamente.");
                    }else{
                        System.out.println("No se pudo crear el equipo.");}
                    break;

                case 8:
                    System.out.print("Nombre Equipo a eliminar: "); 
                    String nomEqBaja = sc.nextLine();


                    if(sistema.bajaEquipo(nomEqBaja)) {
                        System.out.println("Equipo eliminado.");
                    }else {
                        System.out.println("No se pudo eliminar el equipo.");}
                    break;

                case 9:
                    // no cambiar nombre
                    System.out.print("Nombre del Equipo a modificar: ");
                    String nomEqMod = sc.nextLine();

                    System.out.print("Nuevo Puntaje exigido: ");
                    int nPtsExigidos = sc.nextInt();

                    System.out.print("Nuevo Puntaje total: ");
                    int nPtsTotal = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nueva Habitacion actual: "); 
                    String nHabActual = sc.nextLine();
                    
                    System.out.print("Nuevo Puntaje en habitacion: "); 
                    int nPtsHab = sc.nextInt();
                    sc.nextLine();
                    
                    if(sistema.modificarEquipo(nomEqMod, nPtsExigidos, nHabActual, nPtsTotal, nPtsHab)){
                        System.out.println("Equipo modificado exitosamente.");
                    }else{ System.out.println("Error al modificar el equipo.");}
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opc erroena.");
            }

            }while(opc != 0);
           

    }





    //consultas sobre habi sub
    private static void menuHabitaciones(SistemaEscapeHouse sistema, Scanner sc) {
        int opc;
        do {
        
            System.out.println("1. Mostrar Habi");
            System.out.println("2. Habitaciones Contiguas");
            System.out.println("3. Es posible llegar acumulando X pts");
            System.out.println("4. Minimo Puntaje entre dos habi");
            System.out.println("5. Caminos sin pasar por una habi");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion ");

            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    System.out.print("Codigo Habi: ");
                    String codHab = sc.nextLine();
                    System.out.println(sistema.mostrarHabitacion(codHab));
                    break;

                case 2:
                    System.out.print("Codigo Habi: ");
                    String codHabCont = sc.nextLine();
                    System.out.println(sistema.habitacionesContiguas(codHabCont));
                    break;

                case 3:
                    System.out.print("Habi Origen: ");
                    String orig = sc.nextLine();
                    System.out.print("Habi Destino: ");
                    String dest = sc.nextLine();
                    System.out.print("Puntaje X Necesario: ");
                    int x = sc.nextInt();
                    sc.nextLine();
                    System.out.println(sistema.esPosibleLlegar(orig, dest, x));
                    break;

                case 4:
                    System.out.print("Habi Origen: ");
                    String h1 = sc.nextLine();
                    System.out.print("Habi Destino: ");
                    String h2 = sc.nextLine();
                    System.out.println(sistema.minimoPuntaje(h1, h2));
                    break;

                case 5:
                    System.out.print("Habi Origen: ");
                    String o = sc.nextLine();
                    System.out.print("Habi Destino: ");
                    String d = sc.nextLine();
                    System.out.print("Habi Prohibida: ");
                    String proh = sc.nextLine();
                    System.out.print("Puntaje Max (P): ");
                    int punt = sc.nextInt();
                    sc.nextLine();
                    System.out.println(sistema.sinPasarPor(o, d, proh, punt));
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opc erroena.");
            }
        } while (opc != 0);
    }


    //consultas desaf
    private static void menuDesafios(SistemaEscapeHouse sistema, Scanner sc) {
        int opc;
        do {
            System.out.println("Preguntas desafio");
            System.out.println("1. Mostrar Desaf");
            System.out.println("2. Mostrar Desaf Resueltos por Equipo");
            System.out.println("3. Verificar si Equipo resolvio Desaf");
            System.out.println("4. Mostrar Desaf por Rango y Tipo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion ");

            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    System.out.print("Pts del Desaf: ");
                    int pts = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Codigo Habi: ");
                    String hab = sc.nextLine();
                    System.out.println(sistema.mostrarDesafio(pts, hab));
                    break;

                case 2:
                    System.out.print("Nombre Equipo: ");
                    String nomEq = sc.nextLine();
                    System.out.println("Desafios resuestos por: " + nomEq);
                    System.out.println(sistema.mostrarDesafiosResueltos(nomEq));
                    break;

                case 3:
                    System.out.print("Nombre Equipo: ");
                    String eq = sc.nextLine();
                    System.out.print("Codigo Habi: ");
                    String h = sc.nextLine();
                    System.out.print("Pts Desaf: ");
                    int ptsDes = sc.nextInt();
                    sc.nextLine();
                    System.out.println(sistema.verificarDesafioResuelto(eq, h, ptsDes));
                    break;

                case 4:
                    System.out.print("Codigo Habi: ");
                    String codH = sc.nextLine();
                    System.out.print("Pts Min (A): ");
                    int ptsmin = sc.nextInt();
                    System.out.print("Pts Max (B): ");
                    int ptsMax = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tipo desaf: ");
                    String tipo = sc.nextLine();
                    System.out.println(sistema.mostrarDesafiosTipo(codH, ptsmin, ptsMax, tipo));
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opc erroena.");
            }
        } while (opc != 0);
    }

    //consulta equipos
    private static void menuEquipos(SistemaEscapeHouse sistema, Scanner sc) {
        int opc;
        do {
            System.out.println("Consulta equipos");
            System.out.println("1. Mostrar Info Equipo");
            System.out.println("2. Posibles Desaf para pasar de habi");
            System.out.println("3. Jugar Desaf");
            System.out.println("4. Cambiar de Habi");
            System.out.println("5. ¿Puede salir del juego?");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion ");

            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    //Mostrar la info del equipo
                    System.out.print("Nombre Equipo: ");
                    String nomEq = sc.nextLine();
                    System.out.println(sistema.mostrarInfoEquipo(nomEq));
                    break;

                case 2:
                    //Desafios que puede hacer un equipo para pasar de habitacion
                    System.out.print("Nombre Equipo: ");
                    String eq = sc.nextLine();
                    System.out.print("Habi Destino: ");
                    String dest = sc.nextLine();
                    System.out.println(sistema.posiblesDesafios(eq, dest));
                    break;

                case 3:
                    //Jugar desafio
                    System.out.print("Nombre Equipo: ");
                    String equi = sc.nextLine();
                    System.out.print("Codigo Habi: ");
                    String habi = sc.nextLine();
                    System.out.print("Pts Desaf: ");
                    int pts = sc.nextInt();
                    sc.nextLine();
                    if (sistema.jugarDesafio(equi, habi, pts)) {
                        System.out.println("Desaf resuelto.");
                    } else {
                        System.out.println("No se pudo jugar.");
                    }
                    break;

                case 4:
                    //Cambiar de habitacion
                    System.out.print("Nombre del Equipo: ");
                    String equiCambio = sc.nextLine();
                    System.out.print("Habitación de Destino: ");
                    String habDestino = sc.nextLine();
                    if (sistema.cambiarDeHabitacion(equiCambio, habDestino)) {
                        System.out.println("Cambio de habitación exitoso.");
                    } else {
                        System.out.println("No se pudo cambiar de habitación.");
                    }
                    break;

                case 5:
                    //Mostrar si cumple las condiciones para salir
                    System.out.print("Nombre Equipo: ");
                    String eqSalir = sc.nextLine();
                    
                    String msj=sistema.puedeSalir(eqSalir);
                    System.out.println(msj);
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Opc erroena.");
            }
        } while (opc != 0);
    }





}