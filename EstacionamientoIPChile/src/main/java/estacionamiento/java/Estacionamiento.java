//paquete que almacena el programa
package estacionamiento.java;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

// Clase principal que extiende JFrame y representa la aplicación de estacionamiento.
public class Estacionamiento extends JFrame {
    // Mapa que almacena las patentes y sus registros de estacionamiento.
    private static Map<String, RegistroEstacionamiento> registros = new HashMap<>();
    // Constante que define el costo por minuto de estacionamiento.
    private static final double COSTO_POR_MINUTO = 35.0;

    // Método principal que inicia la aplicación.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             // Crear una instancia de Estacionamiento.
            Estacionamiento estacionamiento = new Estacionamiento();
            // Configurar la ventana.
            estacionamiento.setTitle("Estacionamiento IPChile");
            estacionamiento.setSize(400, 200);
            estacionamiento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Colocar componentes en la ventana y hacerla visible.
            estacionamiento.placeComponents();
            estacionamiento.setVisible(true);
        });
    }
    // Método que configura los componentes en la interfaz gráfica.
    private void placeComponents() {
        // Crear un panel y configurar el diseño nulo.
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        // Etiqueta y campo de texto para la patente.
        JLabel patenteLabel = new JLabel("Patente:");
        patenteLabel.setBounds(10, 20, 80, 25);
        panel.add(patenteLabel);

        JTextField patenteText = new JTextField(20);
        patenteText.setBounds(100, 20, 165, 25);
        panel.add(patenteText);

        // Botones para registrar entrada y salida.
        JButton entradaButton = new JButton("Registrar Entrada");
        entradaButton.setBounds(10, 50, 150, 25);
        panel.add(entradaButton);

        JButton salidaButton = new JButton("Registrar Salida");
        salidaButton.setBounds(180, 50, 150, 25);
        panel.add(salidaButton);
        
        // Etiqueta que muestra el costo por minuto.
        JLabel costoLabel = new JLabel("El costo por minuto es de $35 pesos.");
        costoLabel.setBounds(10, 90, 250, 25);
        panel.add(costoLabel);
        
        JLabel trabajadorLabel = new JLabel("Parquimetro: Juan Rojas  Rut: 20.345.233-1");
        trabajadorLabel.setBounds(10, 120, 250, 25);
        panel.add(trabajadorLabel);
        
         // Acción al hacer clic en el botón de entrada.
        entradaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la patente y la hora actual.
                String patente = patenteText.getText();
                LocalTime horaEntrada = LocalTime.now();
                // Agregar el registro de entrada al mapa.
                registros.put(patente, new RegistroEstacionamiento(horaEntrada, null));
                JOptionPane.showMessageDialog(null, "Entrada registrada con éxito.");
            }
        });
        // Acción al hacer clic en el botón de salida.
        salidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la patente.
                String patente = patenteText.getText();

                // Verificar si la patente está registrada.
                if (registros.containsKey(patente)) {
                    // Obtener la hora de salida actual.
                    LocalTime horaSalida = LocalTime.now();
                    // Obtener el registro correspondiente.
                    RegistroEstacionamiento registro = registros.get(patente);
                    // Establecer la hora de salida en el registro.
                    registro.setHoraSalida(horaSalida);

                    // Calcular el tiempo y costo de estacionamiento.
                    long minutosEstacionados = registro.calcularMinutosEstacionados();
                    double costoTotal = calcularCosto(minutosEstacionados);

                    // Mostrar un mensaje con los detalles y el costo.
                    JOptionPane.showMessageDialog(null,
                            "Hora de entrada: " + registro.getHoraEntrada() +
                                    "\nHora de salida: " + registro.getHoraSalida() +
                                    "\nTiempo estacionado: " + minutosEstacionados + " minutos" +
                                    "\nCosto total: $" + costoTotal);

                    // Eliminar la patente del mapa después de registrar la salida.
                    registros.remove(patente);
                } else {
                    // Mostrar un mensaje si la patente no está registrada.
                    JOptionPane.showMessageDialog(null, "La patente no se encuentra registrada.");
                }
            }
        });
    }

    // Método para calcular el costo total en base a los minutos estacionados.
    private double calcularCosto(long minutosEstacionados) {
        return minutosEstacionados * COSTO_POR_MINUTO;
    }
}
//Clase que contiene el registro del estacionamiento
class RegistroEstacionamiento {
    private LocalTime horaEntrada;
    private LocalTime horaSalida;

    public RegistroEstacionamiento(LocalTime horaEntrada, LocalTime horaSalida) {
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public long calcularMinutosEstacionados() {
        return horaEntrada.until(horaSalida, java.time.temporal.ChronoUnit.MINUTES);
    }
}
