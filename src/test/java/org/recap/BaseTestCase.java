package org.recap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.recap.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@WebAppConfiguration
@Transactional
@Rollback()
public class BaseTestCase {

    @Autowired
    public BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    public ItemDetailsRepository itemDetailsRepository;

    @Autowired
    public HoldingsDetailsRepository holdingDetailRepository;

    @Autowired
    public ItemStatusDetailsRepository itemStatusDetailsRepository;

    @Autowired
    public InstitutionDetailsRepository institutionDetailRepository;

    @Autowired
    public CollectionGroupDetailsRepository collectionGroupDetailRepository;

    @Autowired
    public ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;

    @Autowired
    public CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Autowired
    public RequestItemDetailsRepository requestItemDetailsRepository;

    @Autowired
    public UserDetailsRepository userRepo;

    @Autowired
    public RolesDetailsRepositorty roleRepository;

    @Autowired
    public PermissionsDetailsRepository permissionsRepository;

    @Autowired
    public RequestTypeDetailsRepository requestTypeDetailsRepository;


    @Test
    public void loadContexts() {
        System.out.println();
    }
}