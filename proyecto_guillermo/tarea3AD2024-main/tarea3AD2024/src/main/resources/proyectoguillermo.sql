-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-02-2026 a las 16:23:40
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectoguillermo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documentacion`
--

CREATE TABLE `documentacion` (
  `id_documento` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id_empresa` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiante`
--

CREATE TABLE `estudiante` (
  `apellidos` varchar(255) DEFAULT NULL,
  `curso` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `id_profesorado` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_empresa`
--

CREATE TABLE `formacion_empresa` (
  `id_formacion` bigint(20) NOT NULL,
  `activa` bit(1) NOT NULL,
  `id_empresa` bigint(20) DEFAULT NULL,
  `id_estudiante` bigint(20) DEFAULT NULL,
  `id_periodo` bigint(20) DEFAULT NULL,
  `id_tutor_empresa` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `periodo_practicas`
--

CREATE TABLE `periodo_practicas` (
  `id_periodo` bigint(20) NOT NULL,
  `curso_academico` varchar(255) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `nombre_periodo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesorado`
--

CREATE TABLE `profesorado` (
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguimiento`
--

CREATE TABLE `seguimiento` (
  `id_seguimiento` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `valoracion` varchar(255) DEFAULT NULL,
  `id_formacion` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tutor_empresa`
--

CREATE TABLE `tutor_empresa` (
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `id_empresa` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` bigint(20) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `perfil` enum('ADMINISTRADOR','ESTUDIANTE','PROFESORADO','TUTOREMPRESA') NOT NULL,
  `usuario` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `contrasena`, `perfil`, `usuario`) VALUES
(1, 'admin', 'ADMINISTRADOR', 'admin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Indices de la tabla `documentacion`
--
ALTER TABLE `documentacion`
  ADD PRIMARY KEY (`id_documento`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id_empresa`),
  ADD UNIQUE KEY `UKnfu2qgep9eyw4f7jpxoxix8ci` (`email`);

--
-- Indices de la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `FKmenrjhsd13lflddccb1h0riw5` (`id_profesorado`);

--
-- Indices de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD PRIMARY KEY (`id_formacion`),
  ADD KEY `FK68cjmnnqontq0rc8chgl81gk` (`id_empresa`),
  ADD KEY `FKofj00jwpvhgaa0wfa5f58xlxp` (`id_estudiante`),
  ADD KEY `FKc0h7ovpc5qqr24wyttfkw2ggi` (`id_periodo`),
  ADD KEY `FK6lg6p3ib22y4ww4a6wu65jgsl` (`id_tutor_empresa`);

--
-- Indices de la tabla `periodo_practicas`
--
ALTER TABLE `periodo_practicas`
  ADD PRIMARY KEY (`id_periodo`);

--
-- Indices de la tabla `profesorado`
--
ALTER TABLE `profesorado`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UK7wv3vv5uqaehcb7lt8t5f7do1` (`email`);

--
-- Indices de la tabla `seguimiento`
--
ALTER TABLE `seguimiento`
  ADD PRIMARY KEY (`id_seguimiento`),
  ADD KEY `FKrlqqv3tf1syhpw6y5fxqyg7w1` (`id_formacion`);

--
-- Indices de la tabla `tutor_empresa`
--
ALTER TABLE `tutor_empresa`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UK1soxt69hre1elw6gnl81ypn2f` (`email`),
  ADD KEY `FK22qt1q3wya2kldrpidkgvwgvg` (`id_empresa`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `UKi02kr8ui5pqddyd7pkm3v4jbt` (`usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `documentacion`
--
ALTER TABLE `documentacion`
  MODIFY `id_documento` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id_empresa` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  MODIFY `id_formacion` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `periodo_practicas`
--
ALTER TABLE `periodo_practicas`
  MODIFY `id_periodo` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `seguimiento`
--
ALTER TABLE `seguimiento`
  MODIFY `id_seguimiento` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `FKpt2bj0l5q4npigarogy7p1834` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD CONSTRAINT `FK5a2y49gqj50k58rcckgh5cdue` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `FKmenrjhsd13lflddccb1h0riw5` FOREIGN KEY (`id_profesorado`) REFERENCES `profesorado` (`id_usuario`);

--
-- Filtros para la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD CONSTRAINT `FK68cjmnnqontq0rc8chgl81gk` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`),
  ADD CONSTRAINT `FK6lg6p3ib22y4ww4a6wu65jgsl` FOREIGN KEY (`id_tutor_empresa`) REFERENCES `tutor_empresa` (`id_usuario`),
  ADD CONSTRAINT `FKc0h7ovpc5qqr24wyttfkw2ggi` FOREIGN KEY (`id_periodo`) REFERENCES `periodo_practicas` (`id_periodo`),
  ADD CONSTRAINT `FKofj00jwpvhgaa0wfa5f58xlxp` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiante` (`id_usuario`);

--
-- Filtros para la tabla `profesorado`
--
ALTER TABLE `profesorado`
  ADD CONSTRAINT `FKfuvmp5sqdau53m50n7369fck` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `seguimiento`
--
ALTER TABLE `seguimiento`
  ADD CONSTRAINT `FKrlqqv3tf1syhpw6y5fxqyg7w1` FOREIGN KEY (`id_formacion`) REFERENCES `formacion_empresa` (`id_formacion`);

--
-- Filtros para la tabla `tutor_empresa`
--
ALTER TABLE `tutor_empresa`
  ADD CONSTRAINT `FK22qt1q3wya2kldrpidkgvwgvg` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`),
  ADD CONSTRAINT `FKkqwebsfoihwy5gde6usg23phq` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
