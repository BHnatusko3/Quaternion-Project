
       Hierarchical Scenes

This renderer allows us to build "hierarchical scenes".
A hierarchical scene is a scene where some models within
the scene are formed into groups and we can move a group
of models as a single entity while we can also move any
individual model within the group.

For a good example of a group of models in a scene, think of a table with
a lamp and a bowl on it. Once the table, lamp, and bowl are positioned in
a scene, if we move the table, it is pretty likely that the lamp and bowl
should move, as a group, with the table. But we should also be able to
reposition the bowl or the lamp on the table. The table, bowl and lamp
form a group. Now place four chairs around the table. The table (with the
bowl and lamp) along with the four chairs can also be though of as a group
that needs to be placed and move around the scene together. The table with
the lamp and bowl are a sub-group of the whole group. If we move just the
table a bit, the lamp and bowl on the table should move with the table. And
we can adjust the position of any of the four chairs around the table while
we adjust the position of the lamp or the bowl on the table. Now create a
scene of a room with several tables, some chairs around each table, and
several objects placed on each table. This would be an example of a
hierarchical scene with several groups of models (one group for each table,
objects on the table, and chairs around the table) and each group would
contain a nested subgroup of models (each table with its objects on the
table).

As a slightly more complex example of a hierarchical scene, think of a
solar system with a sun, several planets orbiting around the sun, and
several moons orbiting around each planet. The solar system as a whole
is a group of models that we should be able to position in our universe.
Each planet with its moons is a sub-group of models within the solar
system. When a planet moves, its moons should move with it, but the
moons also move around the planet.


We create hierarchical scenes by giving each Position object
a list of (nested) Position objects. This creates a tree data
structure of Position objects. Each (internal) node of the tree
forms a group of all its sub nodes. If we change the Matrix in a
Position node, then we are moving the Model in that Position
object but we are also moving all of the models in the tree's
Position objects that are below that Position node.

We modify the renderer's Pipeline.java file to do a pre-order
depth-first-traversal of the tree of Position nodes. The "visit
node" operation (that is the "pre-order" part of the traversal)
uses the node's Matrix to update a "current transformation matrix"
(ctm) and then renders the node's Model using the ctm in the
pipeline's first stage, model-to-view transformation.


Changes
=======

There are no new files. The only modified files are
   Position.java,
   Renderer.java.

There are several new client programs that make use
of the nested position feature.


Pipeline
========

Our pipeline for rendering a Scene is unchanged from the previous
one. The only change to the renderer is that it has to iterate over
all the nested positions of each position in the scene and build a
"current transformation matrix" (ctm).


        v0 ...  vn      A Model's Vertex objects
         \     /
           \ /
            |
            | model coordinates (of v0 ... vn)
            |
        +-------+
        |       |
        |   P1  |    Model-to-view (ctm) transformation (of the vertices)
        |       |
        +-------+
            |
            | view coordinates (of v0 ... vn)
            |
        +-------+
        |       |
        |   P2  |    View-to-camera (normalization) transformation (of the vertices)
        |       |
        +-------+
            |
            | camera coordinates (of v0 ... vn)
            |
        +-------+
        |       |
        |   P3  |    Projection (of the vertices)
        |       |
        +-------+
            |
            | image plane coordinates (of v0 ... vn)
            |
           / \
          /   \
         /     \
        |   P4  |   Clipping (of each line segment)
         \     /
          \   /
           \ /
            |
            | image plane coordinates (of the clipped vertices)
            |
            |
           / \
          /   \
         /     \
        |   P5  |   Rasterization (of each clipped line segment)
         \     /
          \   /
           \ /
            |
            |  pixels (for each clipped line segment)
            |
           \|/
        FrameBuffer
