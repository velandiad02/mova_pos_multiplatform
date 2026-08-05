# Mova POS Multiplatform

Aplicación POS multiplataforma desarrollada con Kotlin Multiplatform y Compose Multiplatform para Android y Desktop (JVM). El proyecto centraliza la lógica compartida en [shared](./shared), mientras que [androidApp](./androidApp) y [desktopApp](./desktopApp) exponen las implementaciones específicas de cada plataforma.

## ✅ Propósito del proyecto

Mova POS está orientado a cubrir flujos de venta y gestión de transacciones en un entorno de punto de venta, incluyendo:

- selección de comercio y terminal,
- navegación entre pantallas de pago y procesamiento,
- historial de transacciones,
- detalle de cada operación,
- persistencia local de datos y flujo de navegación estructurado.

## 🏗️ Arquitectura

La arquitectura del proyecto está organizada de forma modular y orientada a features.

### Estructura general

- [androidApp](./androidApp): aplicación Android nativa que inicializa la UI y el entorno de ejecución.
- [desktopApp](./desktopApp): aplicación Desktop/JVM para ejecución en Windows/macOS/Linux.
- [shared](./shared): módulo compartido con la mayor parte de la lógica y la interfaz.

### Módulos internos de shared

- [shared/src/commonMain](./shared/src/commonMain): código común a todas las plataformas.
- [shared/src/androidMain](./shared/src/androidMain): adaptaciones específicas de Android.
- [shared/src/jvmMain](./shared/src/jvmMain): adaptaciones específicas de Desktop/JVM.

### Capas principales

- Presentation: componentes de UI, pantallas y navegación con Decompose.
- Domain: modelos, casos de uso y contratos de repositorios.
- Data: repositorios, DAOs, entidades Room y clientes de red.
- Core: inyección de dependencias, base de datos, diseño de sistema y utilidades compartidas.

La navegación principal se gestiona desde el árbol de componentes raíz, con pantallas encadenadas mediante un stack de navegación.

## 🧰 Stack tecnológico

- Kotlin Multiplatform
- Compose Multiplatform
- Decompose para navegación basada en componentes
- Koin para inyección de dependencias
- Room + SQLite para persistencia local
- Ktor para consumo de servicios y networking
- Kotlinx Serialization para JSON
- Gradle 9 + AGP 9

## ▶️ Ejecución

### Requisitos previos

- JDK 11 o superior
- Android SDK configurado si vas a ejecutar la app en Android
- Un dispositivo o emulador Android para pruebas locales

### Ejecutar la aplicación Android

```bash
./gradlew :androidApp:assembleDebug
```

### Ejecutar la aplicación Desktop

```bash
./gradlew :desktopApp:run
```

## 📁 Estructura del proyecto

```text
androidApp/       # Aplicación Android
desktopApp/       # Aplicación Desktop/JVM
shared/           # Código compartido KMP
  src/commonMain/  # Lógica y UI compartidas
  src/androidMain/ # Implementaciones Android
  src/jvmMain/     # Implementaciones Desktop/JVM
```

## 🧠 Decisiones de diseño

- Se eligió Kotlin Multiplatform para compartir la mayor parte del código entre Android y Desktop, reduciendo duplicación.
- Compose Multiplatform permite mantener una UI consistente en ambas plataformas con un enfoque moderno.
- Decompose se usa para modelar la navegación como un árbol de componentes, en lugar de depender únicamente de un enfoque tradicional de Activity/Fragment.
- Room se utiliza para almacenamiento local persistente y soporte de datos offline o de referencia local.
- Koin facilita la inyección de dependencias sin introducir tanta complejidad en el arranque de la app.
- La organización por features ayuda a mantener el proyecto escalable y más fácil de evolucionar.

### Alcance, pruebas y política offline

El alcance actual se ha definido para Android y Desktop porque responde a los escenarios operativos más relevantes del flujo POS: dispositivos Android para interacción de ventas y estaciones Desktop para entornos de caja o administración. Esta decisión permite enfocar el esfuerzo de desarrollo en plataformas con mayor impacto para el negocio, reducir la complejidad de mantenimiento y evitar extender el proyecto a otras plataformas antes de validar el producto en los entornos objetivo.

Para validar el desarrollo se priorizan emuladores o dispositivos físicos Android con distintas resoluciones y versiones del sistema, además de entornos locales de Windows y otras estaciones compatibles con JVM.

La aplicación implementa una estrategia offline-first para la gestión de datos. La información se obtiene inicialmente desde la base de datos local, lo que permite una respuesta rápida y el funcionamiento sin conexión. Posteriormente, cuando existe conectividad, los datos se sincronizan con los servicios externos para mantener la información actualizada. En el caso de las transacciones, si no es posible enviarlas al backend, estas se almacenan localmente con un estado de sincronización pendiente y son procesadas automáticamente mediante WorkManager en Android y un Scheduler en Desktop cuando la conexión se restablece, actualizando su estado una vez completada la sincronización.


## ⚠️ Limitaciones y consideraciones

- Actualmente el proyecto está configurado para Android y Desktop/JVM.
- La capa de red y servicios está preparada, pero la integración real con el backend puede requerir ajustes según el entorno y los contratos de API.
- Algunas funcionalidades de negocio pueden necesitar más validaciones y pruebas de integración conforme el proyecto evolucione.
- Para ejecutar correctamente la app en Android, es necesario contar con el entorno de desarrollo Android correctamente configurado.
- La experiencia offline depende de que los datos y flujos críticos estén correctamente persistidos y reintentos de sincronización estén bien cubiertos en pruebas reales.


## 👤 Autores y Contactos

**Desarrolladores**: Daniel Velandia y Fabián Guerrero

Para preguntas o soporte, puedes contactarme en:

- **Email**: [velandiad02@gmail.com](velandiad02@gmail.com)
- **LinkedIn**: [velandiad02](https://www.linkedin.com/in/velandiad02/)
- **Email**: [frguerrerogo@gmail.com](frguerrerogo@gmail.com)
- **LinkedIn**: [frguerrerogo](https://www.linkedin.com/in/frguerrerogo/)

**Última actualización:** Agosto 5 de 2026