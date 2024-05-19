package com.example;


import java.util.ArrayList;
import java.util.Comparator;

public class SistemaControlAcceso {
    public static void main(String[] args) {
        CapturaImagenes capturaImagenes1 = new CapturaImagenes("JuanPerez", "baja");
        CapturaImagenes capturaImagenes2 = new CapturaImagenes("RodrigoGomez", "media");
        CapturaImagenes capturaImagenes3 = new CapturaImagenes("PatricioSilvera", "baja");
        CapturaImagenes capturaImagenes4 = new CapturaImagenes("JuanRoman", "Alta");
        CapturaImagenes capturaImagenes5 = new CapturaImagenes("DiegoAguirre", "Alta");
        ReconocimientoFacial reconocimientoFacial = new ReconocimientoFacial();
        AlmacenamientoVideo almacenamientoVideo = new AlmacenamientoVideo();
        MonitoreoTiempoReal monitoreoTiempoReal = new MonitoreoTiempoReal();
        ControlAcceso controlAcceso = new ControlAcceso();

        capturaImagenes1.start();
        capturaImagenes2.start();
        capturaImagenes3.start();
        capturaImagenes4.start();
        capturaImagenes5.start();
        reconocimientoFacial.start();
        almacenamientoVideo.start();
        monitoreoTiempoReal.start();
        controlAcceso.start();
    }
}

class CapturaImagenes extends Thread {
    String[] datosCarasDetectadas = ManejadorArchivosGenerico.leerArchivo("C:\\Users\\juani\\OneDrive - Universidad Católica del Uruguay\\Sistemas Operativos\\Obligatorio\\Obligatorio-SO\\demo\\src\\main\\java\\com\\example\\carasDetectadas.txt");
    ArrayList<String[]> datos = new ArrayList<>();
    private String nombre;
    private String importancia;

    public CapturaImagenes(String nombre, String importancia) {
        this.nombre = nombre;
        this.importancia = importancia;
    }

    public void run() {
        try {
            for (String linea : datosCarasDetectadas) {
                String[] elementos = linea.split(",");
                datos.add(elementos);
            }

            System.out.println("Capturando imágenes...");
            Thread.sleep(1000);

            String nuevaEntrada = nombre + "," + importancia;
            ManejadorArchivosGenerico.escribirArchivo("C:\\Users\\juani\\OneDrive - Universidad Católica del Uruguay\\Sistemas Operativos\\Obligatorio\\Obligatorio-SO\\demo\\src\\main\\java\\com\\example\\carasDetectadas.txt", new String[]{nuevaEntrada});
            System.out.println("Nueva entrada agregada: " + nuevaEntrada);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ReconocimientoFacial extends Thread {
    public void run() {
        try {
            while (true) {
                System.out.println("Reconociendo rostros...");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class AlmacenamientoVideo extends Thread {
    public void run() {
        try {
            while (true) {
                System.out.println("Almacenando video...");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class MonitoreoTiempoReal extends Thread {
    public void run() {
        try {
            while (true) {
                System.out.println("Monitoreando en tiempo real...");
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ControlAcceso extends Thread {
    public void run() {
        try {
            while (true) {
                System.out.println("Controlando acceso...");

                String[] datosCarasLeidas = ManejadorArchivosGenerico.leerArchivo("C:\\Users\\juani\\OneDrive - Universidad Católica del Uruguay\\Sistemas Operativos\\Obligatorio\\Obligatorio-SO\\demo\\src\\main\\java\\com\\example\\carasDetectadas.txt");
                ArrayList<String[]> datos = new ArrayList<>();

                for (String linea : datosCarasLeidas) {
                    String[] elementos = linea.split(",");
                    if (elementos.length == 2) {
                        datos.add(elementos);
                    }
                }

                datos.sort((Comparator<? super String[]>) new Comparator<String[]>() {
                    public int compare(String[] o1, String[] o2) {
                        return getImportanceValue(o2[1]) - getImportanceValue(o1[1]);
                    }
                });
                
                for (String[] persona : datos) {
                    String nombre = persona[0];
                    String importancia = persona[1];
                    if (importancia.equalsIgnoreCase("baja")) {
                        System.out.println("Acceso denegado para: " + nombre);
                    } else if (importancia.equalsIgnoreCase("media")) {
                        System.out.println("Acceso permitido para: " + nombre);
                    } else if (importancia.equalsIgnoreCase("alta")) {
                        System.out.println("Acceso prioritario para: " + nombre);
                    }
                }

                Thread.sleep(4000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int getImportanceValue(String importancia) {
        switch (importancia.toLowerCase()) {
            case "alta":
                return 3;
            case "media":
                return 2;
            case "baja":
                return 1;
            default:
                return 0;
        }
    }
}