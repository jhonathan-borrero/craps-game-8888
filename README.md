# Documentación del Proyecto: Ahorcado Eclipse

## Descripción General
Este proyecto es una implementación del juego "Ahorcado" con una temática visual de eclipse solar, desarrollado en Java utilizando JavaFX para la interfaz gráfica. El usuario debe adivinar una palabra secreta letra por letra, con un sistema de intentos y ayudas visuales.

## Estructura del Proyecto
- **src/main/java/com/eclipse/ahorcado/proyecto1/**
  - `HelloApplication.java`: Clase principal que inicia la aplicación JavaFX.
  - `HelloController.java`: Controlador de la vista principal.
  - `Launcher.java`: Permite lanzar la aplicación desde entornos sin soporte de módulos.
  - **controllers/**
    - `GameController.java`: Controlador principal del juego. Gestiona la lógica de interacción, intentos, ayudas y efectos visuales.
    - `InitController.java`: Controlador de la pantalla de inicio, donde se ingresa la palabra secreta.
  - **models/**
    - `GameModel.java`: Modelo de datos del juego. Almacena la palabra secreta, intentos restantes y lógica de validación.
- **src/main/resources/com/eclipse/ahorcado/proyecto1/**
  - `game.fxml`: Vista principal del juego.
  - `init.fxml`: Vista de inicio.
  - `hello-view.fxml`: Vista de bienvenida.
  - **images/**: Recursos gráficos utilizados en la interfaz.

## Principales Clases y Métodos

### GameController.java
- `initialize()`: Inicializa el controlador y prepara la interfaz.
- `generateWordFields()`: Genera los campos de texto para cada letra de la palabra secreta, limpiando los anteriores.
- `handleVerifyLetter()`: Gestiona la verificación de letras, disminuye intentos y bloquea el juego si se acaban.
- `help()`: Proporciona hasta 3 ayudas al usuario.
- `updateSunEffect()`: Actualiza el efecto visual del sol según los intentos restantes.

### GameModel.java
- `GameModel()`: Constructor, inicializa el modelo con valores por defecto.
- `getRemainingAttempts()`: Devuelve los intentos restantes.
- `decreaseAttempt()`: Disminuye el contador de intentos.
- `isGameOver()`: Indica si el juego ha terminado.

### InitController.java
- Gestiona la recepción de la palabra secreta y la transición al juego.

## Recursos Visuales
- **Eclipse_solar_con_corona_ardiente.png**: Imagen utilizada como logo del juego (no genera efectos visuales).

## Ejecución
Para ejecutar el proyecto:
1. Asegúrate de tener Java **versión 17** (o superior) y Maven instalados.
2. Ejecuta `mvnw javafx:run` desde la raíz del proyecto.

## Créditos
Desarrollado por:
- Jhon Esteban Acosta, código: 2518388
- Jhonatan David Borrero, código: 2520360

---

> Documentación generada automáticamente siguiendo las mejores prácticas de JavaDoc e IntelliJ IDEA.
