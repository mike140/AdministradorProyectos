-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-05-2015 a las 08:36:03
-- Versión del servidor: 5.6.20
-- Versión de PHP: 5.5.15

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto_usuario`
--

CREATE TABLE IF NOT EXISTS `proyecto_usuario` (
`ID` int(6) NOT NULL,
  `USUARIO_ID` int(6) NOT NULL,
  `PROYECTO_ID` int(6) NOT NULL,
  `NIVEL` int(10) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea_usuario`
--

CREATE TABLE IF NOT EXISTS `tarea_usuario` (
`ID` int(6) NOT NULL,
  `USUARIO_ID` int(6) NOT NULL,
  `TAREA_ID` int(6) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

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
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `USUARIO_UNICO` (`USUARIO_ID`,`PROYECTO_ID`), ADD KEY `USUARIO_ID` (`USUARIO_ID`,`PROYECTO_ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `TAREA_UNICA` (`TITULO`,`PROYECTO_ID`), ADD KEY `PROYECTO_ID` (`PROYECTO_ID`);

--
-- Indices de la tabla `tarea_usuario`
--
ALTER TABLE `tarea_usuario`
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `USUARIO_UNICO` (`USUARIO_ID`,`TAREA_ID`), ADD KEY `USUARIO_ID` (`USUARIO_ID`,`TAREA_ID`), ADD KEY `TAREA_ID` (`TAREA_ID`);

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
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `proyecto_usuario`
--
ALTER TABLE `proyecto_usuario`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `tarea_usuario`
--
ALTER TABLE `tarea_usuario`
MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
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
