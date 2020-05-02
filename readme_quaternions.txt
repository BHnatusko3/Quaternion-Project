This is a list of the new classes added in the Quaternions project.

--Quaternions folder
-Quaternion: A class that represents Quaternions. 
-Slerp: A class that represents spherical interpolation between two quaternions. 
In other words, a list of quaternions, basically.
-Spot3D: A parody of the Point3D class. Functions both as a 3D point and a 3D vector.
Necessary for SlerpModelRotate2.
--Renderer folder: Quaternion Tests
-Quaternion Tester 1: Tests every combination of positivity, negativity, and zero for 
the four coefficients with the toString method.
-Quaternion Tester 2: Tests the basic quaternion function with premade quaternions
 and a premade matrix.
-Quaternion Tester 3: Tests the basic quaternion function with quaternions made from
command line arguments.
-Quaterinon Tester 3_1: Functions the same a the previous, but with Standard Input 
instead of command line arguments.
-Quaterinon Tester 4: Tests matrix conversion and a couple Euler Angles-related methods.
-Quaternion Tester 5: Tests axis-angle conversion with data (x,y,z,angle) from
Standard Input.
--Renderer folder: Slerp Tests
-Slerp Tester 1: Tests a slerp made from a simple 90-degree Z rotation.
-Slerp Tester 2: Tests a slerp made from one custom Quaternion formed
from standard input data.
-Slerp Tester 3: Functions like the previous class, but with 
two quaternions instead. Slerp is from m to n.
-Slerp Tester 4: Functions like the previous class, but with
three quaternions, using the continue function to continue Slerp
mn to quaternion o.