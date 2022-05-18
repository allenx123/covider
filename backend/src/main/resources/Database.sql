CREATE DATABASE IF NOT EXISTS covider;

CREATE TABLE IF NOT EXISTS `profiles` (
        `email` VARCHAR(255) NOT NULL PRIMARY KEY,
        `firstname` VARCHAR(255),
        `lastname` VARCHAR(255),
        `type` VARCHAR(255),
        `password` VARCHAR(255)        
);

CREATE TABLE IF NOT EXISTS `buildings` (
        `name` VARCHAR(255) NOT NULL PRIMARY KEY,
        `risk` VARCHAR(255),
        `latitude` VARCHAR(255),
        `longitude` VARCHAR(255) 
);

CREATE TABLE IF NOT EXISTS `timeslots` (
		`id` VARCHAR(255) NOT NULL PRIMARY KEY,
		`email` VARCHAR(255),
		`course` VARCHAR(255),
		`building` VARCHAR(255),
		`teacher` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `checkins` (
		`id` VARCHAR(255) NOT NULL PRIMARY KEY,
		`email` VARCHAR(255),
		`building` VARCHAR(255),
		`social_distance` BOOL,
		`wearing_masks` BOOL,
		`hand_sanitizer` BOOL,
		`ppe_provided` BOOL
);

CREATE TABLE IF NOT EXISTS `frequentlyvisited` (
		`id` VARCHAR(255) NOT NULL PRIMARY KEY,
		`email` VARCHAR(255),
		`building` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `healthreports` (
		`email` VARCHAR(255) NOT NULL PRIMARY KEY,
		`infected` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `notified` (
		`email` VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS `courses` (
		`id` VARCHAR(255) NOT NULL PRIMARY KEY,
		`instructor` VARCHAR(255),
		`course` VARCHAR(255),
		`status` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `coursenotify` (
		`id` VARCHAR(255) NOT NULL PRIMARY KEY,
		`email` VARCHAR(255),
		`course` VARCHAR(255),
		`teacher` VARCHAR(255),
		`status` VARCHAR(255)
);

Insert into buildings
values('Andrus Gerontology Center', 'low', '34.02004393352106', '-118.29051698757758');

Insert into buildings
values('Annenberg School for Communication and Journalism', 'low', '34.02198377630302', '-118.28607312127654');

Insert into buildings
values('Biegler Hall','low', '34.02057116101106',  '-118.2885112021023');

Insert into buildings
values('Bovard Auditorium','low','34.02086197097705',  '-118.28555463044077');

Insert into buildings
values('Davidson Conference Center', 'low','34.021673060689', '-118.28067194287677');

Insert into buildings
values('Doheny Memorial Library', 'low','34.02022501127694', '-118.28368452946063');

Insert into buildings
values('Fertitta Hall', 'low','34.01888634810811', '-118.28240980188346');

Insert into buildings 
values('George Lucas Building', 'low','34.023547343719464', '-118.28665324086269');

Insert into buildings 
values('Glorya Kaufman International Dance Center', 'low','34.02351760493726','-118.28524496881317');

Insert into buildings
values('Grace Ford Salvatori Hall', 'low','34.02130220772559', '-118.28805092930772');

Insert into buildings
values('Hancock Foundation Building', 'low','34.019763095443686', '-118.28496864323212');

Insert into buildings
values('Hedco Neurosciences Building', 'low','34.02093762153925', '-118.28791218838843');

Insert into buildings
values('Hoffman Hall', 'low','34.01858736077343', '-118.28535583289043');

Insert into buildings
values('Kaprielian Hall', 'low','34.0226311868487', '-118.2909921442135');

Insert into buildings
values('Leavey Library', 'low','34.021749233020074', '-118.28278983875579');

Insert into buildings
values('Leventhal School of Accounting', 'low','34.01920115953504', '-118.28554225702439');

Insert into buildings
values('Lewis Hall', 'low','34.01943561083296', '-118.28355253049946');

Insert into buildings
values('Michelson Hall', 'low','34.02200356455622', '-118.28931369329618');

Insert into buildings
values('Montgomery Ross Fisher Building', 'low','34.02234501336981', '-118.28248728294712');

Insert into buildings values('Olin Hall of Engineering', 'low','34.0206665098141', '-118.2897507382265');

Insert into buildings values('Physical Education Building', 'low','34.021355145708526', '-118.28632107579514');

Insert into buildings values('Popovich Hall', 'low','34.018843059766596', '-118.282992808313');

Insert into buildings values('Ramo Hall', 'low','34.022680947916236', '-118.28519773389873');

Insert into buildings values('Ray R. Irani Hall','low','34.02232095314006', '-118.2899488128216');

Insert into buildings values('Ronald Tutor Hall','low','34.02019114576486', '-118.2899138808733');

Insert into buildings values('Ronald Tutor Campus Center','low','34.020339808493326', '-118.2863515236851');

Insert into buildings values('Salvatori Computer Science Center','low','34.01954551805852', '-118.28952722085981');

Insert into buildings values('Seaver Science Library','low','34.019629101027675', '-118.28880009202477');

Insert into buildings values('Seeley G. Mudd Building','low','34.02119225765615', '-118.28926136274022');

Insert into buildings values('Social Sciences Building','low','34.02161762025045', '-118.28378281687579');

Insert into buildings values('Spatial Sciences Institute','low','34.01933603536267', '-118.28483792540443');

Insert into buildings values('Stauffer Science Lecture Hall','low', '34.019705043052525', '-118.28739775341455');

Insert into buildings values('Taper Hall','low','34.02227413003116','-118.28456617650207');

Insert into buildings values('Verna and Peter Dauterive Hall','low','34.01896561273786', '-118.28393539485256');

Insert into buildings values('Vivian Hall of Engineering','low','34.02012687143624', '-118.28819446136393');

Insert into buildings values('Waite Phillips Hall','low','34.02201625365964',' -118.2837829714418');

Insert into buildings values('Wallis Annenberg Hall','low','34.02091036699116',' -118.28706163256324');
