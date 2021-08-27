### particle-plus
Minecraft particle mod.
## Usage (WIP)
root:
```
//particle
```
The `//particle` command will show a random internal effect while no other parameters were given.

create effect (create particle group):
```
//particle create [effect name] [effect parameters] -> root
```
Create a new particle group with given effect (shape, text, image etc.)
Create effect must be the first operation in a `//particle` command.
It is possible to create multiple particle group in one command, but not recommended to do so.
# Static arttributes
position:
```
//particle pos <position> -> root
```
Set the initial position of the particle group, default value: <~ ~1 ~>.

rotation:
```
//particle rotation <euler angle> -> root
```
Set the rotation of the particle group, default value: <0 0 0>.

facing:
```
//particle facing <direction vector> angle -> root
//particle facing <entity> angle -> root
```
Another way to set the rotation of the particle group.
It can facing toward a point in worldspace, or an entity.

color:
```
//particle color [rgba] -> root
//particle color [color name] -> root
```
Set color of the particle group.

life:
```
//particle life [life-time] -> root
```
Set the lifetime of the aprticle.

## Particle animation
```
//particle anim [anim name]
//particle anim {NBT anim list}
```
The NBT tag controlled animation.

# Animation NBT format:
>type: int
>    0 - Instant set value. (default)
>    1 - Linear key frame.
>    2 - Bezier key frame.
>    3 - Mathmatic expression.
>attribute: string
>    Attribute name.

# Type specific attributes
>**Instant set value**
>v: (attribute specific)
>   The value of the attribute.
>t: int
>   Time (in tick) to set the value.

>**Linear key frame**
>v0: (attribute specific)
>   Initial value
>v1: (attribute specific)
>   End value
>t0: int
>   Begin time(tick)
>t1: int
>   End time(tick)

>**Bezier key frame**
>v: list(attribute specific)
>   Control points
>t0: int
>   Begin time(tick)
>t1: int
>   End time(tick)

>**Mathmatic expression**
>expression: String
>   Math expression

## External script
```
//particle script [script name]
```
The mod allows you run a external script (or software) to control behavior of particles.

The script communicates with game by passing byte-arrays.
