-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-05-2015 a las 04:24:21
-- Versión del servidor: 5.5.39
-- Versión de PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `tareas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE IF NOT EXISTS `mensaje` (
`ID` int(6) NOT NULL,
  `PROYECTO_ID` int(6) NOT NULL,
  `USUARIO_ID` int(6) NOT NULL,
  `MENSAJE` varchar(300) NOT NULL,
  `FECHA` varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`ID`, `PROYECTO_ID`, `USUARIO_ID`, `MENSAJE`, `FECHA`) VALUES
(1, 1, 1, 'Debes actualizar el proyecto', '18/05/2015'),
(2, 1, 2, 'Lo he actualziado', '18/05/2015');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE IF NOT EXISTS `proyecto` (
`ID` int(6) NOT NULL,
  `TITULO` varchar(100) NOT NULL,
  `DESCRPCION` varchar(300) NOT NULL,
  `FECHA` varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`ID`, `TITULO`, `DESCRPCION`, `FECHA`) VALUES
(1, 'PROYECTO PROGRA 3', 'Calendario', '20/05/2015'),
(2, 'Codigo Etica', 'Nucleo', '25/05/2015'),
(3, 'Guia Mate 4', 'Guia Final ', '22/05/2015'),
(4, 'Examen IT', 'Examen cap 12 IT essentials', '18/05/2015'),
(5, 'Juego', 'Competencia videojuegos', '20/05/2015');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto_usuario`
--

CREATE TABLE IF NOT EXISTS `proyecto_usuario` (
`ID` int(6) NOT NULL,
  `USUARIO_ID` int(6) NOT NULL,
  `PROYECTO_ID` int(6) NOT NULL,
  `NIVEL` int(10) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `proyecto_usuario`
--

INSERT INTO `proyecto_usuario` (`ID`, `USUARIO_ID`, `PROYECTO_ID`, `NIVEL`) VALUES
(1, 1, 1, 1),
(2, 2, 1, 2),
(3, 3, 1, 2),
(4, 4, 3, 1),
(5, 5, 3, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_proyecto`
--

CREATE TABLE IF NOT EXISTS `registro_proyecto` (
`ID` int(6) NOT NULL,
  `PROYECTO_ID` int(6) NOT NULL,
  `ACCION` varchar(300) NOT NULL,
  `FECHA` varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `registro_proyecto`
--

INSERT INTO `registro_proyecto` (`ID`, `PROYECTO_ID`, `ACCION`, `FECHA`) VALUES
(1, 1, 'Se ha actualizado el proyecto', '18/05/2015'),
(2, 4, 'Se ha comenzado la investigación del cap 12', '18/05/2015');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE IF NOT EXISTS `tarea` (
`ID` int(6) NOT NULL,
  `TITULO` varchar(100) NOT NULL,
  `DESCRIPCION` varchar(300) NOT NULL,
  `FECHA_INICIO` varchar(50) NOT NULL,
  `FECHA_FIN` varchar(50) NOT NULL,
  `PROYECTO_ID` int(6) NOT NULL,
  `ESTADO` tinyint(1) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`ID`, `TITULO`, `DESCRIPCION`, `FECHA_INICIO`, `FECHA_FIN`, `PROYECTO_ID`, `ESTADO`) VALUES
(1, 'Documentacion', 'Realizar la documentación del codigo', '18/05/2015', '19/05/2015', 1, 0),
(2, 'Manuales', 'Manuales de Usuario y Programador', '19/05/2015', '19/05/2015', 1, 0),
(3, 'Firmas', 'Núcleo: 3 firmas para el código de ética', '20/05/2015', '21/05/2015', 2, 0),
(4, 'Estudiar IT', 'Investigar cap 12', '18/05/2015', '18/05/2015', 4, 0),
(5, 'Borrador', 'Borrador para el primer nivel del juego', '18/05/2015', '19/05/2015', 5, 0),
(6, 'Portada', 'Diseñar portada y logo del programa', '18/05/2015', '19/05/2015', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea_usuario`
--

CREATE TABLE IF NOT EXISTS `tarea_usuario` (
`ID` int(6) NOT NULL,
  `USUARIO_ID` int(6) NOT NULL,
  `TAREA_ID` int(6) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `tarea_usuario`
--

INSERT INTO `tarea_usuario` (`ID`, `USUARIO_ID`, `TAREA_ID`) VALUES
(1, 1, 1),
(3, 1, 6),
(2, 2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
`ID` int(6) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(100) NOT NULL,
  `CORREO` varchar(100) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `NOMBRE`, `APELLIDO`, `CORREO`, `PASSWORD`) VALUES
(1, 'Miguel Angel', 'Silva Martínez', 'micky_1408sands@hotmail.com', 'mike'),
(2, 'Oscar Emmanuel', 'Yañez Hernandez', 'oskr12@hotmail.com', 'oscar'),
(3, 'Agustin Irvin', 'Garcia Perez', 'guty12@hotmail.com', 'agus'),
(4, 'Gerardo', 'Franco Delgado', 'geraz12@hotmail.com', 'geraz'),
(5, 'admin', 'admin', 'admin', 'pass');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
 ADD PRIMARY KEY (`ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`,`USUARIO_ID`), ADD KEY `USUARIO_ID` (`USUARIO_ID`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
 ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `proyecto_usuario`
--
ALTER TABLE `proyecto_usuario`
 ADD PRIMARY KEY (`ID`), ADD KEY `USUARIO_ID` (`USUARIO_ID`,`PROYECTO_ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`);

--
-- Indices de la tabla `registro_proyecto`
--
ALTER TABLE `registro_proyecto`
 ADD PRIMARY KEY (`ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
 ADD PRIMARY KEY (`ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`);

--
-- Indices de la tabla `tarea_usuario`
--
ALTER TABLE `tarea_usuario`
 ADD PRIMARY KEY (`ID`), ADD KEY `USUARIO_ID` (`USUARIO_ID`,`TAREA_ID`), ADD KEY `TAREA_ID` (`TAREA_ID`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `proyecto_usuario`
--
ALTER TABLE `proyecto_usuario`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `registro_proyecto`
--
ALTER TABLE `registro_proyecto`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `tarea_usuario`
--
ALTER TABLE `tarea_usuario`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mensaje`
--
ALTER TABLE `mensaje`
ADD CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`PROYECTO_ID`) REFERENCES `proyecto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `proyecto_usuario`
--
ALTER TABLE `proyecto_usuario`
ADD CONSTRAINT `proyecto_usuario_ibfk_1` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `proyecto_usuario_ibfk_2` FOREIGN KEY (`PROYECTO_ID`) REFERENCES `proyecto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `registro_proyecto`
--
ALTER TABLE `registro_proyecto`
ADD CONSTRAINT `registro_proyecto_ibfk_1` FOREIGN KEY (`PROYECTO_ID`) REFERENCES `proyecto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tarea`
--
ALTER TABLE `tarea`
ADD CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`PROYECTO_ID`) REFERENCES `proyecto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tarea_usuario`
--
ALTER TABLE `tarea_usuario`
ADD CONSTRAINT `tarea_usuario_ibfk_1` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `tarea_usuario_ibfk_2` FOREIGN KEY (`TAREA_ID`) REFERENCES `tarea` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
