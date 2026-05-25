# WebAsServices

Repositorio oficial de la materia **Aplicaciones Web Orientada a Servicios**. Aquí encontrarás todos los códigos de ejemplo que usaremos en clase y la base para que realices tus prácticas.

[www.frexus.dev](https://www.frexus.dev)

---

## 📚 Estructura del Repositorio

> ⚠️ **Para alumnos:** Solo deben modificar los archivos dentro de la carpeta indicada para cada práctica. El resto del repositorio es material de ejemplo del profesor.

| Carpeta | Propósito | ¿Los alumnos modifican? |
|---------|-----------|------------------------|
| `APIRest/` | Ejemplo base de API "Hola Mundo" y plantilla para la primera práctica. | ✅ **Sí** (aquí harán sus entregas) |
| `demo_soa/` | Demostraciones de arquitectura orientada a servicios. | ❌ No (solo lectura) |
| *(Futuras carpetas)* | *Se irán explicando en clase según se agreguen.* | *Dependerá del ejercicio* |

---

## 🚀 Flujo de Trabajo para Alumnos (Cómo entregar tu práctica)

Seguirás el flujo estándar de colaboración en GitHub: **Fork → Clonar → Modificar → Pull Request**.

### Paso 1: Haz un Fork
1. Asegúrate de tener una [cuenta de GitHub gratuita](https://github.com/join).
2. Ve al repositorio original: `https://github.com/ajgutierr3z/WebAsServices`
3. Haz clic en el botón **Fork** (esquina superior derecha).

### Paso 2: Clona tu Fork
```bash
git clone https://github.com/TU_USUARIO/WebAsServices.git
cd WebAsServices
```

### Paso 3: Modifica (solo la carpeta que se indicada)
Ejemplo para la primera práctica: crea un archivo dentro de APIRest/ con tu nombre:
```php
# Si usas PHP
echo "<?php echo 'Hola desde [Tu Nombre]'; ?>" > APIRest/tu_nombre.php

# Si usas Python
echo "print('Hola desde [Tu Nombre]')" > APIRest/tu_nombre.py
```
### Paso 4: Sube los cambios
```bash
git add .
git commit -m "Práctica: [Tu Nombre Completo]"
git push origin main
```
### Paso 5: Crea el Pull Request (PR)
- Ve a tu fork en GitHub.
- Haz clic en "Compare & pull request" (botón amarillo).
- Verifica que diga:
´´´bash
base repository: ajgutierr3z/WebAsServices ←
head repository: TU_USUARIO/WebAsServices
´´´
- Escribe un título (ej. "Entrega de [Tu Nombre]") y haz clic en "Create pull request".
- ✅ Listo. Tu entrega queda registrada. No es necesario que yo haga merge.
> 📖 Para más detalles, consulta la guía completa en el blog de Frexus (actualizar enlace).

---

### ¿Qué esperar después de tu PR?
| Acción del profesor	| ¿Qué significa para ti?|
|---------------------|------------------------|
|Comentario en el PR	| Revisé tu código, puede tener feedback para mejorar.|
|PR cerrado (sin merge)|	Entregaste correctamente, la práctica queda registrada.|
|PR cerrado (con merge)|	Tu código fue incorporado al repositorio principal (casos excepcionales).|
---
### ¿Por qué usar Pull Requests y no enviar archivos?
- Aprendes el flujo real que se usa en todas las empresas de software.
- Recibes feedback en el código línea por línea.
- Todo queda trazable para tu portafolio.

### Licencia y contacto
- Profesor: ajgutierr3z
- Blog: frexus.dev
- Propósito: Exclusivamente educativo.
- Licencia: MIT (puedes usar el código, pero cita la fuente).

