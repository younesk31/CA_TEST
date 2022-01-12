package facades;

import dtos.MapDTO;
import entities.MapInfo;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MapFacade {

    private static EntityManagerFactory emf;
    private static MapFacade instance;

    private MapFacade() {
    }

    public static MapFacade getMapFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MapFacade();
        }
        return instance;
    }


    public MapDTO getMapInfo (MapDTO mapDTO) {
        EntityManager em = emf.createEntityManager();
        MapInfo map = new MapInfo(mapDTO);
//        Role userRole = new Role("user");
//        user.addRole(userRole);

        try {
            em.getTransaction().begin();
            em.persist(map);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MapDTO(map);
    }
}
