class Hero {
  
    private Integer x, y, person_health;
    private boolean is_person_dead;

    Hero(int _x, int _y, Integer pers_health) {
      
        x = _x;
        y = _y;
        person_health = pers_health;
        is_person_dead = false;
      
    }

    void damage_of_person(Integer _damage) {
      
        if (_damage > person_health) {
            is_person_dead = true; person_health = 0;
        } else {
            person_health = person_health - _damage; is_person_dead = false;
        }
      
    }

    public Integer get_X() { return x; }
    public Integer get_Y() { return y; }

    public boolean is_person_Dead()    { return is_person_dead;   }
    public Integer get_person_Health() { return person_health; }
  
}
