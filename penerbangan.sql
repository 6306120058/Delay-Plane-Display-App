-- phpMyAdmin SQL Dump
-- version 2.10.2
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: May 10, 2014 at 07:24 PM
-- Server version: 5.0.45
-- PHP Version: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `db_penerbangan`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `bandara`
-- 

CREATE TABLE `bandara` (
  `id_bandara` int(6) NOT NULL auto_increment,
  `nama_bandara` varchar(30) NOT NULL,
  `lokasi` varchar(30) NOT NULL,
  PRIMARY KEY  (`id_bandara`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `bandara`
-- 

INSERT INTO `bandara` VALUES (1, 'Soekarno - Hatta', 'Jakarta');
INSERT INTO `bandara` VALUES (2, 'Husein', 'Bandung');

-- --------------------------------------------------------

-- 
-- Table structure for table `maskapai`
-- 

CREATE TABLE `maskapai` (
  `id_maskapai` int(6) NOT NULL auto_increment,
  `nama_maskapai` varchar(30) NOT NULL,
  `website` varchar(30) NOT NULL,
  `phone` varchar(12) NOT NULL,
  PRIMARY KEY  (`id_maskapai`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `maskapai`
-- 

INSERT INTO `maskapai` VALUES (1, 'Garuda', 'garudaindo', '02212345');
INSERT INTO `maskapai` VALUES (2, 'Lion Air', 'lionaire', '12344423');

-- --------------------------------------------------------

-- 
-- Table structure for table `penerbangan`
-- 

CREATE TABLE `penerbangan` (
  `id_penerbangan` int(6) NOT NULL auto_increment,
  `id_maskapai` int(6) default NULL,
  `id_pesawat` int(6) default NULL,
  `id_bandara_src` int(6) default NULL,
  `id_bandara_dest` int(6) default NULL,
  `time_src_schd` varchar(10) default NULL,
  `time_src_actl` varchar(10) default NULL,
  `status_src` varchar(30) default NULL,
  `time_dest_schd` varchar(10) default NULL,
  `time_dest_actl` varchar(10) default NULL,
  `status_dest` varchar(30) default NULL,
  PRIMARY KEY  (`id_penerbangan`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `penerbangan`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `pesawat`
-- 

CREATE TABLE `pesawat` (
  `id_pesawat` int(6) NOT NULL auto_increment,
  `type_pesawat` varchar(25) NOT NULL,
  `id_maskapai` int(6) NOT NULL,
  PRIMARY KEY  (`id_pesawat`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `pesawat`
-- 

INSERT INTO `pesawat` VALUES (1, 'G001', 1);
INSERT INTO `pesawat` VALUES (2, 'L002', 2);

-- --------------------------------------------------------

-- 
-- Table structure for table `user`
-- 

CREATE TABLE `user` (
  `id_user` int(6) NOT NULL auto_increment,
  `nama` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `id_bandara` varchar(6) NOT NULL,
  `roles` int(1) NOT NULL,
  PRIMARY KEY  (`id_user`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `user`
-- 

INSERT INTO `user` VALUES (1, 'admin', 'admin', 'admin', '1', 0);
INSERT INTO `user` VALUES (2, 'soeta', 'soeta', 'soeta', '1', 1);
INSERT INTO `user` VALUES (3, 'husen', 'husen', 'husen', '2', 1);
