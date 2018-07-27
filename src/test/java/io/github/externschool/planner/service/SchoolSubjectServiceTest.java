package io.github.externschool.planner.service;

import io.github.externschool.planner.TestPlannerApplication;
import io.github.externschool.planner.entity.SchoolSubject;
import io.github.externschool.planner.repository.SchoolSubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestPlannerApplication.class)
public class SchoolSubjectServiceTest {

    @MockBean
    private SchoolSubjectRepository repository;

    @Autowired
    private SchoolSubjectServiceImpl service;

    private List<SchoolSubject> subjects;
    private Optional<SchoolSubject> optional;

    @Before
    public void setup(){
        subjects = new ArrayList<>();
        for (long i = 0L; i < 4L; i++) {
            SchoolSubject subject = new SchoolSubject();
            subject.setId(i);
            subject.setName(Long.toString(i));
            subjects.add(subject);
        }
        optional = Optional.of(subjects.get(0));
    }

    @Test
    public void shouldReturnSubject_whenFindSubjectById() {
        SchoolSubject expected = subjects.get(0);
        Mockito.when(repository.findById(expected.getId()))
                .thenReturn(optional);

        SchoolSubject actual = service.findSubjectById(expected.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected)
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    public void shouldReturnSubject_whenFindSubjectByName() {
        SchoolSubject expected = subjects.get(0);
        Mockito.when(repository.findByName(expected.getName()))
                .thenReturn(expected);

        SchoolSubject actual = service.findSubjectByName(expected.getName());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected)
                .isEqualToComparingFieldByField(expected);
    }


    @Test
    public void shouldReturnFourSubjects_whenFindAllByOrderByName() {
        Mockito.when(repository.findAllByOrderByName())
                .thenReturn(subjects);
        List<SchoolSubject> actual = service.findAllByOrderByName();

        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(subjects)
                .containsSequence(subjects);
    }

    @Test
    public void shouldReturnTwoSubjects_whenFindAllById() {
        List<Long> indices = Arrays.asList(subjects.get(0).getId(), subjects.get(1).getId());
        List<SchoolSubject> expected = Arrays.asList(subjects.get(0), subjects.get(1));
        Mockito.when(repository.findAllById(indices))
                .thenReturn(expected);
        List<SchoolSubject> actual = service.findAllById(indices);

        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expected)
                .hasSize(2);
    }

    @Test
    public void shouldReturnSubject_whenSaveOrUpdateSubject() {
        SchoolSubject expected = subjects.get(0);
        Mockito.when(repository.save(expected))
                .thenReturn(expected);
        Mockito.when(repository.findById(expected.getId()))
                .thenReturn(optional);

        SchoolSubject actual = service.saveOrUpdateSubject(expected);
        assertThat(service.findSubjectById(actual.getId()))
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    public void shouldInvokeOnce_whenDeleteSubject() {
        SchoolSubject deleted = subjects.get(0);
        Mockito.when(repository.findById(deleted.getId()))
                .thenReturn(optional);

        service.deleteSubject(deleted.getId());

        verify(repository, times(1)).delete(deleted);
    }
}
