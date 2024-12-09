/**
 * 
 */
/**
 * 
 */
module Finals {
	requires java.desktop;
	requires java.sql;
	requires gson;
	requires com.fasterxml.jackson.databind;
    opens Game to gson;
}